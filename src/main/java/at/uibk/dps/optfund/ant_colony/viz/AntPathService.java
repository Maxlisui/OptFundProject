package at.uibk.dps.optfund.ant_colony.viz;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.tutorial.salesman.SalesmanProblem;
import org.opt4j.tutorial.salesman.SalesmanRoute;
import org.opt4j.viewer.IndividualMouseListener;
import org.opt4j.viewer.Viewport;
import org.opt4j.viewer.Widget;
import org.opt4j.viewer.WidgetParameters;

import javax.swing.*;
import java.awt.*;

/**
 * The main logic for the visualization panels.
 * @author Christoph Haas
 */
@Singleton
public class AntPathService implements IndividualMouseListener, OptimizerIterationListener, OptimizerStateListener {
    protected final Viewport viewport;
    protected AntPathWidget widget;
    protected final boolean vizOnStart;
    protected final Archive archive;


    // The path is shown by a double click of a individual in the archive
    // monitor panel. Thus we need the ArchiveMonitorPanel and the main
    // GUIFrame.
    @Inject
    public AntPathService(Viewport viewport, boolean vizOnStart, Archive archive) {
        this.viewport = viewport;
        this.vizOnStart = vizOnStart;
        this.archive = archive;
    }

    // Triggers if one full iteration completed.
    @Override
    public void iterationComplete(int iteration) {
        if(widget == null) {
            return; // no widget, nothing to do
        }
        if(widget.getIndividual() == null && vizOnStart && archive.stream().findFirst().isPresent()) {
            widget.setIndividual(archive.stream().findFirst().get());
        }
        widget.getPanel().repaint();
    }

    // If an individual is clicked with the right mouse button, open a popup
    // menu that contains the option to paint the path.
    @Override
    public void onPopup(Individual individual, Component component, Point p, JPopupMenu menu) {
        JMenuItem menuItem = new JMenuItem("Show Path");
        menuItem.addActionListener(e -> paintPath(individual));
        menu.add(menuItem);
    }

    // If an individual is double clicked, paint the path.
    @Override
    public void onDoubleClick(Individual individual, Component component, Point p) {
        paintPath(individual);
    }

    // Paint the path: Construct a JInternalFrame, add the AntPathPanel and add the
    // frame to the desktop of the main GUIFrame.
    private void paintPath(Individual individual) {
        if(widget == null) {
            widget = new AntPathWidget(individual);
            viewport.addWidget(widget);
        }
    }

    @Override
    public void optimizationStarted(Optimizer optimizer) {
        if(vizOnStart) {
            paintPath(null);
        }
    }

    @Override
    public void optimizationStopped(Optimizer optimizer) {
    }

    @WidgetParameters(title = "Route")
    protected static class AntPathWidget implements Widget {
        Individual individual;
        AntPathPanel pathPanel;

        public AntPathWidget(Individual individual) {
            super();
            this.individual = individual;
        }

        public Individual getIndividual() {
            return individual;
        }

        public void setIndividual(Individual individual) {
            this.individual = individual;
            pathPanel.setIndividual(individual);
        }

        @Override
        public JPanel getPanel() {
            if(pathPanel == null) {
                pathPanel = new AntPathPanel(individual);
            }
            return pathPanel;
        }

        @Override
        public void init(Viewport viewport) { }

    }

    // Panel that paints a single Ant Path
    private static class AntPathPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        protected Individual individual;

        public AntPathPanel(Individual individual) {
            super();
            this.individual = individual;
            setPreferredSize(new Dimension(416, 416));
        }

        public Individual getIndividual() {
            return individual;
        }

        public void setIndividual(Individual individual) {
            this.individual = individual;
        }

        @Override
        protected void paintComponent(Graphics g) {
            if(individual == null) {
                return; // nothing to do
            }
            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2f));
            g2d.clearRect(0, 0, 416, 424);

            SalesmanRoute salesmanRoute = (SalesmanRoute) individual.getPhenotype();

            for (int i = 0; i < salesmanRoute.size(); i++) {
                final int j = (i + 1) % salesmanRoute.size();
                SalesmanProblem.City one = salesmanRoute.get(i);
                SalesmanProblem.City two = salesmanRoute.get(j);

                int x1 = (int) (one.getX() * 4) + 8;
                int y1 = (int) (one.getY() * 4) + 8;
                int x2 = (int) (two.getX() * 4) + 8;
                int y2 = (int) (two.getY() * 4) + 8;

                g2d.drawLine(x1, y1, x2, y2);

                if(i == 0 || i == salesmanRoute.size() - 1)  { // the start/end point
                    g2d.setColor(Color.RED);
                }
                g2d.fillOval(x1 - 2, y1 - 2, 8, 8);
                if(i == 0 || i == salesmanRoute.size() - 1)  {
                    g2d.setColor(Color.BLACK);
                }
            }
        }
    }
}
