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
    protected final Archive archive;
    protected AntPathWidget widget;

    @Inject
    protected boolean vizOnStart;

    @Inject
    protected int vizDelay;


    // The path is shown by a double click of a individual in the archive
    // monitor panel. Thus we need the ArchiveMonitorPanel and the main
    // GUIFrame.
    @Inject
    public AntPathService(Viewport viewport, Archive archive) {
        this.viewport = viewport;
        this.archive = archive;
    }

    // Triggers if one full iteration completed. Used to automatically update the path if visualization is enabled.
    @Override
    public void iterationComplete(int iteration) {
        if(!vizOnStart) {
            return; // no widget, nothing to do
        }

        if(vizDelay > 0) {
            try {
                Thread.sleep(vizDelay);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        widget.setCurrentIteration(iteration);
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
        viewport.addWidget(new AntPathWidget(individual));
    }

    @Override
    public void optimizationStarted(Optimizer optimizer) {
        if(vizOnStart) {
            widget = new AntPathWidget(archive);
            viewport.addWidget(widget);
        }
    }

    @Override
    public void optimizationStopped(Optimizer optimizer) {
    }

    @WidgetParameters(title = "Route")
    protected static class AntPathWidget implements Widget {
        AntPathPanel pathPanel;

        public AntPathWidget(Individual individual) {
            super();
            pathPanel = new AntPathPanel(individual);
        }

        public AntPathWidget(Archive archive) {
            super();
            pathPanel = new AntPathPanel(archive);
        }

        public Individual getIndividual() {
            return pathPanel.getIndividual();
        }

        public void setIndividual(Individual individual) {
            pathPanel.setIndividual(individual);
        }

        public void setCurrentIteration(int iteration) {
            pathPanel.setCurrentIteration(iteration);
        }

        @Override
        public JPanel getPanel() {
            return pathPanel;
        }

        @Override
        public void init(Viewport viewport) { }

    }

    // Panel that paints a single Ant Path
    private static class AntPathPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        protected Individual individual;
        protected Archive archive;
        protected boolean singleMode;
        protected int currentIteration = 0;

        public AntPathPanel(Individual individual) {
            super();
            this.individual = individual;
            this.singleMode = true;
            setPreferredSize(new Dimension(416, 436));
        }

        public AntPathPanel(Archive archive) {
            super();
            this.archive = archive;
            this.singleMode = false;
            setPreferredSize(new Dimension(416, 436));
        }

        public Individual getIndividual() {
            return individual;
        }

        public void setIndividual(Individual individual) {
            this.individual = individual;
        }

        public void setCurrentIteration(int currentIteration) {
            this.currentIteration = currentIteration;
        }

        @Override
        protected void paintComponent(Graphics g) {
            if(!singleMode) {
                individual = archive.stream().findFirst().isPresent() ? archive.stream().findFirst().get() : null;
            }
            if(individual == null) {
                return; // nothing to do
            }
            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2f));
            g2d.clearRect(0, 0, 416, 436);

            if(!singleMode) {
                g2d.drawString(String.valueOf(currentIteration), 5, 430);
            }
            g2d.drawString(String.valueOf(individual.getObjectives().toString()), 150,430);

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
