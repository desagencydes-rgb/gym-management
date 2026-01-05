package gym.ui;

import gym.service.GymService;
import gym.ui.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final GymService service;

    public MainFrame(GymService service) {
        this.service = service;

        setTitle("Gym Management System");
        setSize(1200, 800); // Slightly larger for modern feel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIStyle.BG_COLOR);

        // Main Layout
        setLayout(new BorderLayout());

        // Center Panel (CardLayout)
        CardLayout cardLayout = new CardLayout();
        JPanel centerPanel = new JPanel(cardLayout);
        centerPanel.setBackground(UIStyle.BG_COLOR);

        // Create Panels
        centerPanel.add(new DashboardPanel(service), "Dashboard");
        centerPanel.add(new MembersPanel(service), "Members");
        centerPanel.add(new CoachesPanel(service), "Coaches");
        centerPanel.add(new PlansPanel(service), "Plans");
        centerPanel.add(new SubscriptionsPanel(service), "Subscriptions");
        centerPanel.add(new PaymentsPanel(service), "Payments");
        centerPanel.add(new AttendancePanel(service), "Attendance");

        // Sidebar
        SidebarPanel sidebar = new SidebarPanel(e -> {
            String command = e.getActionCommand(); // Button text is the command
            cardLayout.show(centerPanel, command);
        });

        // Add components
        add(sidebar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        // Default View
        sidebar.setActive("Dashboard");
    }
}
