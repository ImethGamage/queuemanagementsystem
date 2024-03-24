class CircularQueue<T> {
    private final Object[] elements;
    private int front;  // Index of the front element
    private int rear;   // Index of the rear element
    private int size;   // Current size of the queue

    public CircularQueue(int capacity) {
        elements = new Object[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }//Method to check if circular queue is empty

    public boolean isFull() {
        return size == elements.length;
    }//Method to check if circular queue is full

    public void enqueue(T item) {//Method to add item to circular queue
        if (isFull()) {//Check whether queue is full
            System.out.println("Waiting list is full. Customer cannot be added.");
            return;
        }

        rear = (rear + 1) % elements.length;//increments the rear index by 1,
        elements[rear] = item;// assigns the item to the rear index in the elements array.
        size++; //increments the size by 1.
    }

    public T dequeue() {//Method to remove customer from circular queue
        if (isEmpty()) {//Check whether queue is empty
            return null;
        }

        T item = (T) elements[front];
        front = (front + 1) % elements.length; // Increment the 'front' index by 1
        size--;// Decrement the 'size' by 1

        return item;// Return the dequeued item.
    }

}
