package gym.ui.panels;

import gym.model.Member;
import gym.model.MembershipPlan;
import gym.model.Subscription;
import gym.service.GymService;
import gym.ui.UIStyle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SubscriptionsPanel extends JPanel {
    private final GymService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public SubscriptionsPanel(GymService service) {
        this.service = service;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE,
                UIStyle.PADDING_LARGE));

        // Header Section
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIStyle.PADDING_LARGE, 0));

        JLabel titleLabel = UIStyle.createHeaderLabel("Subscriptions");
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton addBtn = UIStyle.createPrimaryButton("New Subscription");
        JButton refreshBtn = UIStyle.createSecondaryButton("Refresh");

        addBtn.addActionListener(e -> showAddSubscriptionDialog());
        refreshBtn.addActionListener(e -> loadSubscriptions());

        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = { "ID", "Member", "Plan", "Start Date", "End Date", "Active" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIStyle.BG_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        loadSubscriptions();
    }

    private void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(60, 60, 60));
        table.setFont(UIStyle.REGULAR_FONT);
        table.setForeground(UIStyle.TEXT_PRIMARY);
        table.setBackground(UIStyle.CARD_COLOR);
        table.setSelectionBackground(UIStyle.ACCENT_COLOR);
        table.setSelectionForeground(Color.WHITE);

        table.getTableHeader().setFont(UIStyle.BUTTON_FONT);
        table.getTableHeader().setBackground(UIStyle.BG_COLOR);
        table.getTableHeader().setForeground(UIStyle.TEXT_SECONDARY);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    private void loadSubscriptions() {
        tableModel.setRowCount(0);
        List<Member> members = service.getAllMembers();
        List<MembershipPlan> plans = service.getAllPlans();

        for (Member m : members) {
            List<Subscription> subs = service.getSubscriptionsForMember(m.getId());
            for (Subscription s : subs) {
                String planName = plans.stream()
                        .filter(p -> p.getId().equals(s.getPlanId()))
                        .findFirst()
                        .map(MembershipPlan::getName)
                        .orElse("Unknown Plan");

                tableModel.addRow(new Object[] {
                        s.getId(), m.getName(), planName, s.getStartDate(), s.getEndDate(), s.isActive()
                });
            }
        }
    }

    private void showAddSubscriptionDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "New Subscription", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UIStyle.CARD_COLOR);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(UIStyle.CARD_COLOR);

        List<Member> members = service.getAllMembers();
        List<MembershipPlan> plans = service.getAllPlans();

        if (members.isEmpty() || plans.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Need at least one member and one plan.");
            return;
        }

        JComboBox<Member> memberCombo = new JComboBox<>(members.toArray(new Member[0]));
        JComboBox<MembershipPlan> planCombo = new JComboBox<>(plans.toArray(new MembershipPlan[0]));

        java.util.function.Consumer<String> addLbl = text -> {
            JLabel l = new JLabel(text);
            l.setForeground(UIStyle.TEXT_PRIMARY);
            formPanel.add(l);
        };

        addLbl.accept("Member:");
        formPanel.add(memberCombo);
        addLbl.accept("Plan:");
        formPanel.add(planCombo);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(UIStyle.CARD_COLOR);
        JButton cancelBtn = UIStyle.createSecondaryButton("Cancel");
        JButton saveBtn = UIStyle.createPrimaryButton("Subscribe");

        cancelBtn.addActionListener(e -> dialog.dispose());
        saveBtn.addActionListener(e -> {
            try {
                Member m = (Member) memberCombo.getSelectedItem();
                MembershipPlan p = (MembershipPlan) planCombo.getSelectedItem();
                service.subscribe(m.getId(), p.getId());
                dialog.dispose();
                loadSubscriptions();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        btnPanel.add(cancelBtn);
        btnPanel.add(saveBtn);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
