package gym.ui.panels;

import gym.model.Member;
import gym.model.enums.Gender;
import gym.service.GymService;
import gym.ui.UIStyle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MembersPanel extends JPanel {
    private final GymService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public MembersPanel(GymService service) {
        this.service = service;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE,
                UIStyle.PADDING_LARGE));

        // Header Section
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIStyle.PADDING_LARGE, 0));

        JLabel titleLabel = UIStyle.createHeaderLabel("Members Management");
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Toolbar Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton addBtn = UIStyle.createPrimaryButton("Add Member");
        JButton deleteBtn = UIStyle.createDestructiveButton("Delete Selected");
        JButton refreshBtn = UIStyle.createSecondaryButton("Refresh");

        addBtn.addActionListener(e -> showAddMemberDialog());
        refreshBtn.addActionListener(e -> loadMembers());
        deleteBtn.addActionListener(e -> deleteSelectedMember());

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Name", "Gender", "Phone", "Email", "Join Date", "Active" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIStyle.BG_COLOR);

        add(scrollPane, BorderLayout.CENTER);

        loadMembers();
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

        // Header styling
        table.getTableHeader().setFont(UIStyle.BUTTON_FONT);
        table.getTableHeader().setBackground(UIStyle.BG_COLOR); // Darker header
        table.getTableHeader().setForeground(UIStyle.TEXT_SECONDARY);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    private void loadMembers() {
        tableModel.setRowCount(0);
        List<Member> members = service.getAllMembers();
        for (Member m : members) {
            tableModel.addRow(new Object[] {
                    m.getId(), m.getName(), m.getGender(), m.getPhone(), m.getEmail(), m.getJoinDate(), m.isActive()
            });
        }
    }

    private void deleteSelectedMember() {
        int row = table.getSelectedRow();
        if (row != -1) {
            String id = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteMember(id);
                loadMembers();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a member to delete");
        }
    }

    private void showAddMemberDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Member", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UIStyle.CARD_COLOR);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(UIStyle.CARD_COLOR);

        JTextField nameField = new JTextField();
        JComboBox<Gender> genderCombo = new JComboBox<>(Gender.values());
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();

        // Helper for labels
        java.util.function.Consumer<String> addLbl = text -> {
            JLabel l = new JLabel(text);
            l.setForeground(UIStyle.TEXT_PRIMARY);
            formPanel.add(l);
        };

        addLbl.accept("Name:");
        formPanel.add(nameField);
        addLbl.accept("Gender:");
        formPanel.add(genderCombo);
        addLbl.accept("Phone:");
        formPanel.add(phoneField);
        addLbl.accept("Email:");
        formPanel.add(emailField);

        dialog.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(UIStyle.CARD_COLOR);
        JButton cancelBtn = UIStyle.createSecondaryButton("Cancel");
        JButton saveBtn = UIStyle.createPrimaryButton("Save");

        cancelBtn.addActionListener(e -> dialog.dispose());
        saveBtn.addActionListener(e -> {
            try {
                service.registerMember(
                        nameField.getText(),
                        (Gender) genderCombo.getSelectedItem(),
                        phoneField.getText(),
                        emailField.getText());
                dialog.dispose();
                loadMembers();
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
