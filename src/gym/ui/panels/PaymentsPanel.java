package gym.ui.panels;

import gym.model.Payment;

import gym.model.enums.PaymentMethod;
import gym.service.GymService;
import gym.ui.UIStyle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PaymentsPanel extends JPanel {
    private final GymService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public PaymentsPanel(GymService service) {
        this.service = service;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE,
                UIStyle.PADDING_LARGE));

        // Header Section
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIStyle.PADDING_LARGE, 0));

        JLabel titleLabel = UIStyle.createHeaderLabel("Payments");
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton addBtn = UIStyle.createPrimaryButton("Record Payment");
        JButton refreshBtn = UIStyle.createSecondaryButton("Refresh");

        addBtn.addActionListener(e -> showRecordPaymentDialog());
        refreshBtn.addActionListener(e -> loadPayments());

        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = { "ID", "Sub ID", "Amount", "Date", "Method" };
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

        loadPayments();
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

    private void loadPayments() {
        tableModel.setRowCount(0);
        List<Payment> payments = service.getAllPayments();
        for (Payment p : payments) {
            tableModel.addRow(new Object[] {
                    p.getId(), p.getSubscriptionId(), p.getAmount(), p.getDate(), p.getMethod()
            });
        }
    }

    private void showRecordPaymentDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Record Payment", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UIStyle.CARD_COLOR);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(UIStyle.CARD_COLOR);

        JTextField subIdField = new JTextField();
        JTextField amountField = new JTextField();
        JComboBox<PaymentMethod> methodCombo = new JComboBox<>(PaymentMethod.values());

        java.util.function.Consumer<String> addLbl = text -> {
            JLabel l = new JLabel(text);
            l.setForeground(UIStyle.TEXT_PRIMARY);
            formPanel.add(l);
        };

        addLbl.accept("Subscription ID:");
        formPanel.add(subIdField);
        addLbl.accept("Amount:");
        formPanel.add(amountField);
        addLbl.accept("Method:");
        formPanel.add(methodCombo);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(UIStyle.CARD_COLOR);
        JButton cancelBtn = UIStyle.createSecondaryButton("Cancel");
        JButton saveBtn = UIStyle.createPrimaryButton("Pay");

        cancelBtn.addActionListener(e -> dialog.dispose());
        saveBtn.addActionListener(e -> {
            try {
                service.recordPayment(
                        subIdField.getText(),
                        Double.parseDouble(amountField.getText()),
                        (PaymentMethod) methodCombo.getSelectedItem());
                dialog.dispose();
                loadPayments();
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
