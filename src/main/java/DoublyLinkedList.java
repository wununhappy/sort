import java.util.Scanner;

public class DoublyLinkedList<T> {
    private Node<T> head;

    public DoublyLinkedList() {
        head = null;
    }
    static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    // 1. 建立一个空表
    // 已经在构造函数中实现了

    // 2. 在第i个位置插入新的元素x
    public void insert(int index, T x) {
        Node<T> newNode = new Node<>(x);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node<T> temp = head;
            if (index == 1) {
                newNode.next = head;
                newNode.prev = head.prev;
                head.prev.next = newNode;
                head.prev = newNode;
                head = newNode;
            } else {
                for (int i = 1; i < index - 1; i++) {
                    temp = temp.next;
                }
                newNode.next = temp.next;
                newNode.prev = temp;
                temp.next.prev = newNode;
                temp.next = newNode;
            }
        }
    }


    // 3. 删除第i个位置上的元素
    public void remove(int i) {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        Node<T> temp = head;
        for (int j = 1; j < i; j++) {
            temp = temp.next;
        }

        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        if (temp == head) {
            head = temp.next;
        }
    }

    // 4. 取第i个位置上的元素
    public T getI(int i) {
        if (head == null) {
            System.out.println("List is empty");
            return null;
        }

        Node<T> temp = head;
        for (int j = 1; j < i; j++) {
            temp = temp.next;
        }

        return temp.data;
    }

    // 5. 返回元素x第一次出现在双向循环链表中的位置号
    public int indexOf(int x) {
        if (head == null) {
            System.out.println("List is empty");
            return -1; // Assuming -1 represents an invalid value
        }

        Node<T> temp = head;
        int index = 1;
        do {
            if (temp.data.equals(x) ) {
                return index;
            }
            temp = temp.next;
            index++;
        } while (temp != head);

        return -1; // Element not found
    }

    // 6. 求双向循环链表的长度，即元素个数
    public int length() {
        if (head == null) {
            return 0;
        }

        Node<T> temp = head;
        int length = 0;
        do {
            length++;
            temp = temp.next;
        } while (temp != head);

        return length;
    }
    // 7. 输出双向循环链表中所有的元素值
    public void out() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        Node<T> temp = head;
        do {
            System.out.print(temp.data + " ");
            temp = temp.next;
        } while (temp != null && temp != head);
        System.out.println();
    }

    // 8. 就地逆置
    public void reverse() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        Node<T> current = head;
        Node<T> prev = null;
        Node<T> next;

        do {
           next = current.next;
           current.next=prev;
           current.prev=next;
           prev=current;
           current=next;

        } while (current != head);

        head.next = prev;
        head = prev;
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        Scanner scan = new Scanner(System.in);
        int n=scan.nextInt();
        for(int i=1;i<=n;i++)
        {
            list.insert(i, scan.nextInt());
        }
        System.out.println("Original List:");
        list.out();
        int n1=scan.nextInt();int n2=scan.nextInt();
        list.insert(n1, n2);
        list.out();
        // 2. 在第i个位置插入新的元素x
        list.insert(1, 10);
        list.insert(2, 20);
        list.insert(3, 30);
        list.insert(4, 40);
        list.insert(5, 50);
        list.insert(1, 60);
        // 7. 输出双向循环链表中所有的元素值
        System.out.println("added List:");
        list.out();

        // 3. 删除第i个位置上的元素
        list.remove(2);

        // 7. 输出双向循环链表中所有的元素值
        System.out.println("List after deleting at position 2:");
        list.out();

        // 4. 取第i个位置上的元素
        System.out.println("Element at position 3: " + list.getI(3));

        // 5. 返回元素x第一次出现在双向循环链表中的位置号
        System.out.println("Index of element 30: " + list.indexOf(30));

        // 6. 求双向循环链表的长度，即元素个数
        System.out.println("Length of the list: " + list.length());

        // 8. 就地逆置
        list.reverse();

        // 7. 输出双向循环链表中所有的元素值
        System.out.println("Reversed List:");
        list.out();
    }
}
