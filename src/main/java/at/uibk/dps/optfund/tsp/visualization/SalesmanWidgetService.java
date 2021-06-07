package at.uibk.dps.optfund.tsp.visualization;

import at.uibk.dps.optfund.ant_colony.model.AbstractAntEdge;
import at.uibk.dps.optfund.ant_colony.model.AbstractAntNode;
import at.uibk.dps.optfund.tsp.SalesmanRoute;
import at.uibk.dps.optfund.tsp.model.City;
import at.uibk.dps.optfund.tsp.model.Street;
import com.google.inject.Inject;
import org.opt4j.core.Individual;
import org.opt4j.viewer.IndividualMouseListener;
import org.opt4j.viewer.Viewport;
import org.opt4j.viewer.Widget;
import org.opt4j.viewer.WidgetParameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Map;

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

    private static class SalesmanPanel extends JPanel implements MouseListener {
        @Serial
        private static final long serialVersionUID = 1L;

        private final SalesmanRoute salesmanRoute;
        private final ArrayList<Rectangle> cityRects;
        private int currentClickedCityIndex = -1;

        public SalesmanPanel(Individual individual) {
            super();
            this.salesmanRoute = (SalesmanRoute) individual.getPhenotype();
            cityRects = new ArrayList<>(salesmanRoute.size());
            setPreferredSize(new Dimension(208, 208));
            addMouseListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            Rectangle size = g2d.getClip().getBounds();

            g2d.setBackground(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2f));
            g2d.clearRect(0, 0, size.width, size.height);

            final double factorX = size.width / 100.0;
            final double factorY = size.height / 100.0;

            cityRects.clear();

            for (int i = 0; i < salesmanRoute.size(); i++) {
                final int j = (i + 1) % salesmanRoute.size();
                City one = salesmanRoute.get(i);
                City two = salesmanRoute.get(j);

                int x1 = (int) (one.getX() * factorX) + 10;
                int y1 = (int) (one.getY() * factorY) + 10;
                int x2 = (int) (two.getX() * factorX) + 10;
                int y2 = (int) (two.getY() * factorY) + 10;

                g2d.drawLine(x1, y1, x2, y2);

                Rectangle cityRect = new Rectangle(x1 - 5, y1 - 5, 10, 10);
                g2d.fillOval(cityRect.x, cityRect.y, cityRect.width, cityRect.height);
                cityRects.add(cityRect);
                g2d.drawString(Integer.toString(i), x1 + 5, y1);
            }

            if(currentClickedCityIndex >= 0) {
                City clickedCity = salesmanRoute.get(currentClickedCityIndex);
                City nextCity = null;

                if(currentClickedCityIndex == salesmanRoute.size() - 1) {
                    nextCity = salesmanRoute.get(0);
                } else {
                    nextCity = salesmanRoute.get(currentClickedCityIndex + 1);
                }

                AbstractAntEdge street = clickedCity.getNeighbours().get(nextCity);

                g2d.drawString("City " + currentClickedCityIndex + ":Pheromone to next Edge: " + street.getPheromone(),
                        0, size.height - 20);

                int yOffset = 10;
                for(Map.Entry<AbstractAntNode, AbstractAntEdge> entry : clickedCity.getNeighbours().entrySet()) {
                    int index = salesmanRoute.indexOf(entry.getKey());
                    g2d.drawString("City " + index + ": Pheromone: " + entry.getValue().getPheromone() + " Distance: " + entry.getValue().getDistance(),
                            0, yOffset);
                    yOffset += 10;
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            currentClickedCityIndex = -1;
            for(int i = 0; i < cityRects.size(); i++) {
                Rectangle rect = cityRects.get(i);
                if(rect.contains(e.getPoint())) {
                    currentClickedCityIndex = i;
                    break;
                }
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
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
