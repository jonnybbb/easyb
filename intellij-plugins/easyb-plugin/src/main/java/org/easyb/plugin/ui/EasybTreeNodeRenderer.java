package org.easyb.plugin.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.easyb.plugin.StepResult;

public class EasybTreeNodeRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        EasybTreeNode node = (EasybTreeNode) value;
        StepResult stepResult = (StepResult) node.getUserObject();

        String stepName = capitalizeName(stepResult.getStepType().toString());

        label.setText(stepName + " " + stepResult.getStepName());
        label.setIcon(loadIcon());

        return label;
    }

    private String capitalizeName(String word) {
        char[] letters = word.toLowerCase().toCharArray();

        StringBuilder builder = new StringBuilder();
        builder.append(Character.toUpperCase(letters[0]));
        for (int i = 1; i < letters.length; i++) {
            builder.append(letters[i]);
        }

        return builder.toString();
    }

    private static Icon loadIcon() {
        return new ImageIcon(EasybTreeNodeRenderer.class.getResource("/success.png"));
    }
}
