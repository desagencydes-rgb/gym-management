package gym.ui.panels;

import gym.model.Attendance;
import gym.model.Member;
import gym.service.GymService;
import gym.ui.UIStyle;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendancePanel extends JPanel {
    private final GymService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public AttendancePanel(GymService service) {
        this.service = service;
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE, UIStyle.PADDING_LARGE,
                UIStyle.PADDING_LARGE));

        // Header Section
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIStyle.PADDING_LARGE, 0));

        JLabel titleLabel = UIStyle.createHeaderLabel("Attendance Log");
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton checkInBtn = UIStyle.createPrimaryButton("Log Check-In");
        JButton refreshBtn = UIStyle.createSecondaryButton("Refresh");

        checkInBtn.addActionListener(e -> showCheckInDialog());
        refreshBtn.addActionListener(e -> loadAttendance());

        buttonPanel.add(checkInBtn);
        buttonPanel.add(refreshBtn);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = { "ID", "Member", "Time" };
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

        loadAttendance();
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

    private void loadAttendance() {
        tableModel.setRowCount(0);
        List<Attendance> list = service.getAllAttendance();
        List<Member> members = service.getAllMembers();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Attendance a : list) {
            String memberName = members.stream()
                    .filter(m -> m.getId().equals(a.getMemberId()))
                    .findFirst()
                    .map(Member::getName)
                    .orElse("Unknown Member");

            tableModel.addRow(new Object[] {
                    a.getId(), memberName, a.getTimestamp().format(formatter)
            });
        }
    }

    private void showCheckInDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Check In", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UIStyle.CARD_COLOR);

        JPanel formPanel = new JPanel(new GridLayout(2, 1, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(UIStyle.CARD_COLOR);

        List<Member> members = service.getAllMembers();
        JComboBox<Member> memberCombo = new JComboBox<>(members.toArray(new Member[0]));

        JLabel l = new JLabel("Select Member:");
        l.setForeground(UIStyle.TEXT_PRIMARY);
        formPanel.add(l);
        formPanel.add(memberCombo);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(UIStyle.CARD_COLOR);
        JButton saveBtn = UIStyle.createPrimaryButton("Check In");

        saveBtn.addActionListener(e -> {
            try {
                Member m = (Member) memberCombo.getSelectedItem();
                service.logAttendance(m.getId());
                dialog.dispose();
                loadAttendance();
                JOptionPane.showMessageDialog(this, "Check-in successful for " + m.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        btnPanel.add(saveBtn);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
