package gym.ui.panels;

import gym.service.GymService;
import gym.ui.UIStyle;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final GymService service;
    private final JLabel memberCountLabel;
    private final JLabel activeSubsLabel;
    private final JLabel revenueLabel;

    public DashboardPanel(GymService service) {
        this.service = service;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE,
                UIStyle.PADDING_LARGE));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(UIStyle.createHeaderLabel("Dashboard Overview"), BorderLayout.WEST);

        JButton refreshBtn = UIStyle.createSecondaryButton("Refresh Data");
        refreshBtn.addActionListener(e -> refreshStats());
        headerPanel.add(refreshBtn, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Stats Grid
        // Stats Grid - Responsive FlowLayout
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, -UIStyle.PADDING_LARGE, 0, 0)); // Compensate for left
                                                                                                // padding

        memberCountLabel = new JLabel("0", SwingConstants.LEFT);
        activeSubsLabel = new JLabel("0", SwingConstants.LEFT);
        revenueLabel = new JLabel("$0.0", SwingConstants.LEFT);

        statsPanel.add(createStatCard("Total Members", memberCountLabel, new Color(40, 167, 69)));
        statsPanel.add(createStatCard("Active Subscriptions", activeSubsLabel, new Color(23, 162, 184)));
        statsPanel.add(createStatCard("Total Revenue", revenueLabel, new Color(255, 193, 7)));

        // wrapper to keep stats at top
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setOpaque(false);
        contentWrapper.add(statsPanel, BorderLayout.NORTH);

        // Placeholder for future charts/tables
        JLabel placeholder = new JLabel("Recent Activity (Coming Soon)", SwingConstants.CENTER);
        placeholder.setFont(UIStyle.REGULAR_FONT);
        placeholder.setForeground(UIStyle.TEXT_SECONDARY);
        contentWrapper.add(placeholder, BorderLayout.CENTER);

        add(contentWrapper, BorderLayout.CENTER);

        refreshStats();
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UIStyle.CARD_COLOR);
        card.setPreferredSize(new Dimension(250, 120)); // Fixed size for wrapping
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, accent), // Color strip
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(UIStyle.REGULAR_FONT);
        titleLbl.setForeground(UIStyle.TEXT_SECONDARY);

        valueLabel.setFont(UIStyle.HEADER_FONT.deriveFont(32f)); // Bigger font for numbers
        valueLabel.setForeground(UIStyle.TEXT_PRIMARY);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    public void refreshStats() {
        memberCountLabel.setText(String.valueOf(service.getMemberCount()));
        activeSubsLabel.setText(String.valueOf(service.getActiveSubscriptionCount()));
        revenueLabel.setText("$" + service.getTotalRevenue());
    }
}
