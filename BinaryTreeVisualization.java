package java_labcodes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Tree{
    int data;
    TreeNode left;
    TreeNode right;

    public Tree(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

class BinaryT {
    private TreeNode root;

    public BinaryT() {
        root = null;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void insert(int data) {
        root = insertRec(root, data);
    }

    private TreeNode insertRec(TreeNode root, int data) {
        if (root == null) {
            root = new TreeNode(data);
            return root;
        }

        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }

        return root;
    }

    public List<Integer> findLeaves(TreeNode node) {
        List<Integer> leaves = new ArrayList<>();
        if (node != null) {
            if (node.left == null && node.right == null) {
                leaves.add(node.data);
            } else {
                leaves.addAll(findLeaves(node.left));
                leaves.addAll(findLeaves(node.right));
            }
        }
        return leaves;
    }

    public int findMin(TreeNode node) {
        if (node == null) {
            throw new IllegalArgumentException("Tree is empty.");
        }
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    public int findMax(TreeNode node) {
        if (node == null) {
            throw new IllegalArgumentException("Tree is empty.");
        }
        while (node.right != null) {
            node = node.right;
        }
        return node.data;
    }

    public void drawTree(Graphics g, int x, int y, TreeNode node) {
        if (node != null) {
            int radius = 20; // Adjust the radius as needed
            int xOffset = 50; // Adjust the horizontal spacing
            int yOffset = 50; // Adjust the vertical spacing

            // Draw the node
            g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g.drawString(String.valueOf(node.data), x - 6, y + 4);

            // Draw left subtree
            if (node.left != null) {
                g.drawLine(x, y, x - xOffset, y + yOffset);
                drawTree(g, x - xOffset, y + yOffset, node.left);
            }

            // Draw right subtree
            if (node.right != null) {
                g.drawLine(x, y, x + xOffset, y + yOffset);
                drawTree(g, x + xOffset, y + yOffset, node.right);
            }
        }
    }
}

public class BinaryTreeVisualization extends JFrame {
    private JPanel treePanel;
    private BinaryTree binaryTree;

    public BinaryTreeVisualization() {
        setTitle("Binary Tree Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        binaryTree = new BinaryTree();

        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                binaryTree.drawTree(g, getWidth() / 2, 30, binaryTree.getRoot());
            }
        };
        add(treePanel);

        // Create Insert button
        JButton insertButton = new JButton("Insert Node");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter a number to insert:");
                try {
                    int value = Integer.parseInt(input);
                    binaryTree.insert(value);
                    treePanel.repaint(); // Redraw the tree after inserting
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                }
            }
        });

        // Create Show Leaves button
        JButton showLeavesButton = new JButton("Show Leaves");
        showLeavesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> leaves = binaryTree.findLeaves(binaryTree.getRoot());
                String leafString = "Leaves: " + leaves.toString();
                JOptionPane.showMessageDialog(null, leafString);
            }
        });

        // Create Find Min button
        JButton findMinButton = new JButton("Find Min");
        findMinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int min = binaryTree.findMin(binaryTree.getRoot());
                    JOptionPane.showMessageDialog(null, "Minimum element: " + min);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Tree is empty.");
                }
            }
        });

        // Create Find Max button
        JButton findMaxButton = new JButton("Find Max");
        findMaxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int max = binaryTree.findMax(binaryTree.getRoot());
                    JOptionPane.showMessageDialog(null, "Maximum element: " + max);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Tree is empty.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(insertButton);
        buttonPanel.add(showLeavesButton);
        buttonPanel.add(findMinButton);
        buttonPanel.add(findMaxButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new BinaryTreeVisualization();
    }
}

