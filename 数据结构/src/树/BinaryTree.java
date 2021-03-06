package 树;

import 树.printer.BinaryTrees;

import java.math.BigDecimal;
import java.util.*;

public class BinaryTree {
    public static void main(String[] args) {
//        Integer[] arr = new Integer[]{1, null, 2, 3};
//        Integer[] arr = new Integer[]{1, 2, 3,4,null,null,5,null,6,null,null,7,8};
//        Character[] chars = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G','H',null,null,'I',null,null,'J'};
        Integer[] arr = new Integer[]{3, 9, 20, null, null, 15, 7};
        TreeNode root = buildBinaryTree(arr);
        System.out.println(Arrays.toString(levelOrder(root)));
//        System.out.println("mirrorTree(root) = " + mirrorTree(root));
//        System.out.println("递归法前序遍历："+preorderTraversal(root, resultList));  //前序遍历：递归法
//        System.out.println("回溯法前序遍历："+preorderRecall(root));   //前序遍历：回溯法
//        System.out.println("递归法中序遍历："+inorderTraversal(root,resultList));
//        System.out.println("回溯法中序遍历：" + inorderRecall(root));
//        System.out.println("后序遍历："+postorderTraversal(root, resultList));
    }

    //翻转二叉树，本质上是遍历二叉树，递归前中后/层序都可以
    public static TreeNode mirrorTree(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (root != null && stack.size() != 0) {
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            if (root.left != null)
                stack.push(root.left);
            if (root.right != null)
                stack.push(root.right);
            root = stack.pop();
        }
        return root;
    }

    /***    二叉树层遍历(广度遍历)
     * 特例处理： 当树的根节点为空，则直接返回空列表 [] ；
     * 初始化： 打印结果列表 res = [] ，包含根节点的队列 queue = [root] ；
     * BFS 循环： 当队列 queue 为空时跳出；
     * 出队： 队首元素出队，记为 node；
     * 打印： 将 node.val 添加至列表 tmp 尾部；
     * 添加子节点： 若 node 的左（右）子节点不为空，则将左（右）子节点加入队列 queue ；
     * 返回值： 返回打印结果列表 res 即可。
     *
     */
    public static int[] levelOrder(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null)
            return new int[0];
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (queue.size() != 0) {
            TreeNode poll = queue.poll();
            list.add((Integer) poll.val);
            if (poll.left != null)
                queue.add(poll.left);
            if (poll.right != null)
                queue.add(poll.right);
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    /**
     * 使用数组构建二叉树（完全二叉树）
     *
     * @param arr
     * @param <T>
     * @return
     */
    public static <T> TreeNode buildBinaryTree(T[] arr) {
        //使用链表，方便补全null
        List<TreeNode> linkedList = new LinkedList<>();
        if (arr.length < 1) return null;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            if (arr[i] == null) linkedList.add(null);
            else linkedList.add(new TreeNode(arr[i]));
        }
        //i树的索引，最高二叉树高度是n/2+1
        for (int i = 0; i <= linkedList.size() / 2 - 1; i++) {
            //父节点为null，就没有子节点
            if (linkedList.get(i) == null) {
                //父节点为null，需要在链表合适位置（2i+1，2i+2）处补上null作为子节点
                linkedList.add(2 * i + 1, null);
                linkedList.add(2 * i + 2, null);
                continue;
            }
            // 左结点是否为空,左节点是否被列出
            if ((2 * i + 1) < linkedList.size() && linkedList.get(2 * i + 1) != null) {
                linkedList.get(i).left = linkedList.get(2 * i + 1);
                double db = Math.log((linkedList.size() / Math.log(2)));
                int depth = new BigDecimal(db).intValue();
            }
            //右节点是否为空,右节点是否被列出
            if ((2 * i + 2) < linkedList.size() && linkedList.get(2 * i + 2) != null) {
                linkedList.get(i).right = linkedList.get(2 * i + 2);
            }
        }
        System.out.println(linkedList.size());
        return linkedList.get(0);
    }

    /**
     * 前序遍历（递归）
     *
     * @param root
     * @param resultList
     * @param <T>
     * @return
     */
    public static <T> List<T> preorderTraversal(TreeNode<T> root, List<T> resultList) {
        if (root == null) return resultList;
        resultList.add(root.val);
        preorderTraversal(root.left, resultList);
        preorderTraversal(root.right, resultList);
        return resultList;
    }

    /**
     * 前序遍历（回溯法）
     *
     * @return
     */
    public static <T> List<T> preorderRecall(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        List<T> list = new ArrayList<T>();
        while (node != null || !stack.isEmpty()) {
            //节点部位空，输出该节点
            while (node != null) {
                list.add((T) node.val);
                stack.push(node);
                node = node.left;
            }
            //左树为空后，考虑右树
            if (!stack.isEmpty()) {
                //回溯节点，获取父节点
                node = stack.pop();
                node = node.right;
            }
        }
        return list;
    }

    /**
     * 二叉树中序遍历（递归法）
     *
     * @param root
     * @param resultList
     * @param <T>
     * @return
     */
    public static <T> List<T> inorderTraversal(TreeNode<T> root, List<T> resultList) {
        if (root == null) return resultList;
        else {
            inorderTraversal(root.left, resultList);
            resultList.add(root.val);
            inorderTraversal(root.right, resultList);
        }
        return resultList;
    }

    /**
     * 中序遍历（回溯法）
     * 永远要先考虑左子树
     *
     * @param root
     * @param <T>
     * @return
     */
    public static <T> List<T> inorderRecall(TreeNode root) {
        List<T> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                list.add((T) root.val);
                root = root.right;
            }
        }
        return list;
    }

    /**
     * 二叉树后序遍历（递归法）
     *
     * @param root
     * @param resultList
     * @param <T>
     * @return
     */
    public static <T> List<T> postorderTraversal(TreeNode<T> root, List<T> resultList) {
        if (root != null) {
            postorderTraversal(root.left, resultList);
            postorderTraversal(root.right, resultList);
            resultList.add(root.val);
        }
        return resultList;
    }

    /**
     * 二叉树后序遍历（回溯法）
     * 思路：后序遍历在决定是否可以输出当前节点的值的时候，需要考虑其左右子树是否都已经遍历完成。所以需要设置一个lastVisit游标。
     * 若lastVisit等于当前考查节点的右子树，表示该节点的左右子树都已经遍历完成，则可以输出当前节点。
     * <p>
     * 并把lastVisit节点设置成当前节点，将当前游标节点node设置为空，下一轮就可以访问栈顶元素。
     * <p>
     * 否者，需要接着考虑右子树，node = node.right
     */
    public static <T> List<T> postorderRollback(TreeNode root) {
        ArrayList<T> arrayList = new ArrayList<>();
        Stack<TreeNode> treeNodeStack = new Stack<TreeNode>();
        TreeNode node = root;
        TreeNode lastVisit = root;
        while (node != null || !treeNodeStack.isEmpty()) {
            while (node != null) {
                treeNodeStack.push(node);
                node = node.left;
            }
            //查看当前栈顶元素
            node = treeNodeStack.peek();
            //如果其右子树也为空，或者右子树已经访问
            //则可以直接输出当前节点的值
            if (node.right == null || node.right == lastVisit) {
                arrayList.add((T) node.val);
                treeNodeStack.pop();
                lastVisit = node;
                node = null;
            } else {
                //否则，继续遍历右子树
                node = node.right;
            }
        }
        return arrayList;
    }
}

/**
 * 二叉树节点结构类
 *
 * @param <T>
 */
class TreeNode<T> {
    public T val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(T val) {
        this.val = val;
    }
}