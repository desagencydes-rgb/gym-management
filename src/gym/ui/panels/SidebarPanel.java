package gym.ui.panels;

import gym.ui.UIStyle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SidebarPanel extends JPanel {
    private final Map<String, JButton> navButtons = new HashMap<>();
    private final ActionListener navigationListener;
    private String currentActiveInfo = "";

    public SidebarPanel(ActionListener navigationListener) {
        this.navigationListener = navigationListener;

        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_COLOR);
        setPreferredSize(new Dimension(200, 0)); // Fixed width sidebar
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(50, 50, 50)));

        // --- Logo Area ---
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        logoPanel.setOpaque(false);
        JLabel logoLabel = UIStyle.createHeaderLabel("GYM MANAGER");
        logoLabel.setForeground(UIStyle.ACCENT_COLOR);
        logoPanel.add(logoLabel);
        add(logoPanel, BorderLayout.NORTH);

        // --- Navigation Buttons ---
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        addNavButton(menuPanel, "Dashboard");
        addNavButton(menuPanel, "Members");
        addNavButton(menuPanel, "Coaches");
        addNavButton(menuPanel, "Plans");
        addNavButton(menuPanel, "Subscriptions");
        addNavButton(menuPanel, "Payments");
        addNavButton(menuPanel, "Attendance");

        // Add spacer to push content up
        menuPanel.add(Box.createVerticalGlue());

        add(menuPanel, BorderLayout.CENTER);

        // --- Footer Area (Optional) ---
        JLabel versionLabel = new JLabel("v1.0.0", SwingConstants.CENTER);
        versionLabel.setFont(UIStyle.REGULAR_FONT.deriveFont(12f));
        versionLabel.setForeground(Color.GRAY);
        versionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(versionLabel, BorderLayout.SOUTH);
    }

    private void addNavButton(JPanel container, String name) {
        JButton btn = new JButton(name);
        btn.setFont(UIStyle.SUBHEADER_FONT);
        btn.setForeground(UIStyle.TEXT_SECONDARY);
        btn.setBackground(UIStyle.BG_COLOR);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false); // Make transparent initially
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        btn.addActionListener(e -> {
            setActive(name);
            navigationListener.actionPerformed(e);
        });

        // Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!name.equals(currentActiveInfo)) {
                    btn.setForeground(Color.WHITE);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!name.equals(currentActiveInfo)) {
                    btn.setForeground(UIStyle.TEXT_SECONDARY);
                }
            }
        });

        navButtons.put(name, btn);
        container.add(btn);
        container.add(Box.createVerticalStrut(5));
    }

    public void setActive(String name) {
        // Reset all
        for (Map.Entry<String, JButton> entry : navButtons.entrySet()) {
            JButton btn = entry.getValue();
            boolean isActive = entry.getKey().equals(name);

            if (isActive) {
                btn.setForeground(UIStyle.ACCENT_COLOR);
                btn.setFont(UIStyle.SUBHEADER_FONT); // Keep bold
                btn.setOpaque(true);
                btn.setBackground(new Color(40, 40, 40)); // Active background
            } else {
                btn.setForeground(UIStyle.TEXT_SECONDARY);
                btn.setFont(UIStyle.SUBHEADER_FONT);
                btn.setOpaque(false);
                btn.setBackground(UIStyle.BG_COLOR);
            }
        }
        currentActiveInfo = name;
    }
}
