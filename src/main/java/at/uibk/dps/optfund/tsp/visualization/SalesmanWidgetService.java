package at.uibk.dps.optfund.tsp.visualization;

import at.uibk.dps.optfund.tsp.SalesmanRoute;
import at.uibk.dps.optfund.tsp.model.City;
import com.google.inject.Inject;
import org.opt4j.core.Individual;
import org.opt4j.viewer.IndividualMouseListener;
import org.opt4j.viewer.Viewport;
import org.opt4j.viewer.Widget;
import org.opt4j.viewer.WidgetParameters;

import javax.swing.*;
import java.awt.*;

public class SalesmanWidgetService implements IndividualMouseListener {

    private final Viewport viewport;

    @Inject
    public SalesmanWidgetService(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public void onPopup(Individual individual, Component component, Point p, JPopupMenu menu) {
        JMenuItem menuItem = new JMenuItem("Show Route");
        menuItem.addActionListener(e -> paintRoute(individual));
        menu.add(menuItem);
    }

    @Override
    public void onDoubleClick(Individual individual, Component component, Point p) {
        paintRoute(individual);
    }

    private void paintRoute(Individual individual) {
        viewport.addWidget(new SalesmanWidget(individual));
    }

    private static class SalesmanPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        private final Individual individual;

        public SalesmanPanel(Individual individual) {
            super();
            this.individual = individual;
            setPreferredSize(new Dimension(208, 208));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2f));
            g2d.clearRect(0, 0, 208, 212);

            SalesmanRoute salesmanRoute = (SalesmanRoute) individual.getPhenotype();

            for (int i = 0; i < salesmanRoute.size(); i++) {
                final int j = (i + 1) % salesmanRoute.size();
                City one = salesmanRoute.get(i);
                City two = salesmanRoute.get(j);

                int x1 = (int) (one.getX() * 2) + 4;
                int y1 = (int) (one.getY() * 2) + 4;
                int x2 = (int) (two.getX() * 2) + 4;
                int y2 = (int) (two.getY() * 2) + 4;

                g2d.drawLine(x1, y1, x2, y2);
                g2d.drawOval(x1 - 2, y1 - 2, 4, 4);
            }
        }
    }

    @WidgetParameters(title = "Route")
    protected static class SalesmanWidget implements Widget {

        protected final Individual individual;

        public SalesmanWidget(Individual individual) {
            super();
            this.individual = individual;
        }

        @Override
        public JPanel getPanel() {
            return new SalesmanPanel(individual);
        }

        @Override
        public void init(Viewport viewport) { }

    }
}
