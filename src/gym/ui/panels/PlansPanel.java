package gym.ui.panels;

import gym.model.MembershipPlan;
import gym.model.enums.PlanDuration;
import gym.service.GymService;
import gym.ui.UIStyle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PlansPanel extends JPanel {
    private final GymService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public PlansPanel(GymService service) {
        this.service = service;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE,
                UIStyle.PADDING_LARGE));

        // Header Section
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIStyle.PADDING_LARGE, 0));

        JLabel titleLabel = UIStyle.createHeaderLabel("Membership Plans");
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton addBtn = UIStyle.createPrimaryButton("Create Plan");
        JButton deleteBtn = UIStyle.createDestructiveButton("Delete Selected");
        JButton refreshBtn = UIStyle.createSecondaryButton("Refresh");

        addBtn.addActionListener(e -> showAddPlanDialog());
        refreshBtn.addActionListener(e -> loadPlans());
        deleteBtn.addActionListener(e -> deleteSelectedPlan());

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = { "ID", "Name", "Price", "Duration" };
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

        loadPlans();
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

    private void loadPlans() {
        tableModel.setRowCount(0);
        List<MembershipPlan> plans = service.getAllPlans();
        for (MembershipPlan p : plans) {
            tableModel.addRow(new Object[] {
                    p.getId(), p.getName(), p.getPrice(), p.getDuration()
            });
        }
    }

    private void deleteSelectedPlan() {
        int row = table.getSelectedRow();
        if (row != -1) {
            String id = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this plan?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.deletePlan(id);
                loadPlans();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a plan to delete");
        }
    }

    private void showAddPlanDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Create Plan", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UIStyle.CARD_COLOR);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(UIStyle.CARD_COLOR);

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JComboBox<PlanDuration> durCombo = new JComboBox<>(PlanDuration.values());

        java.util.function.Consumer<String> addLbl = text -> {
            JLabel l = new JLabel(text);
            l.setForeground(UIStyle.TEXT_PRIMARY);
            formPanel.add(l);
        };

        addLbl.accept("Name:");
        formPanel.add(nameField);
        addLbl.accept("Price:");
        formPanel.add(priceField);
        addLbl.accept("Duration:");
        formPanel.add(durCombo);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(UIStyle.CARD_COLOR);
        JButton cancelBtn = UIStyle.createSecondaryButton("Cancel");
        JButton saveBtn = UIStyle.createPrimaryButton("Save");

        cancelBtn.addActionListener(e -> dialog.dispose());
        saveBtn.addActionListener(e -> {
            try {
                service.createPlan(
                        nameField.getText(),
                        Double.parseDouble(priceField.getText()),
                        (PlanDuration) durCombo.getSelectedItem());
                dialog.dispose();
                loadPlans();
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
