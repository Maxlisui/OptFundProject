package at.uibk.dps.optfund.ant_colony.viz;

import com.google.inject.Inject;
import org.opt4j.core.Individual;
import org.opt4j.viewer.IndividualMouseListener;
import org.opt4j.viewer.Viewport;
import org.opt4j.viewer.Widget;
import org.opt4j.viewer.WidgetParameters;

import javax.swing.*;
import java.awt.*;

public class AntPathService implements IndividualMouseListener {
    protected final Viewport viewport;

    // The path is shown by a double click of a individual in the archive
    // monitor panel. Thus we need the ArchiveMonitorPanel and the main
    // GUIFrame.
    @Inject
    public AntPathService(Viewport viewport) {
        this.viewport = viewport;
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

    // Panel that paints a single Ant Path
    private static class AntPathPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        protected final Individual individual;

        public AntPathPanel(Individual individual) {
            super();
            this.individual = individual;
            setPreferredSize(new Dimension(208, 208));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            Rectangle size = g2d.getClip().getBounds();

            g2d.setBackground(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2f));
            g2d.clearRect(0, 0, size.width, size.height);

            g2d.fillOval(10,10,10,10);

            final double factorX = size.width / 100.0;
            final double factorY = size.height / 100.0;
        }
    }

    @WidgetParameters(title = "Route")
    protected static class AntPathWidget implements Widget {
        final Individual individual;

        public AntPathWidget(Individual individual) {
            super();
            this.individual = individual;
        }

        @Override
        public JPanel getPanel() {
            return new AntPathPanel(individual);
        }

        @Override
        public void init(Viewport viewport) { }

    }
}
