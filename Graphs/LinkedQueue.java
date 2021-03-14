package Graphs;/*
Johnny To
22C 43Z
2.3.2021
 */

public final class LinkedQueue<T> implements QueueInterface<T> {
    private Node firstNode; // in front of queue
    private Node lastNode;  // back or last of queue

    public LinkedQueue(){
        firstNode = null;
        lastNode = null;
    }

    public void enqueue(T newEntry){
        Node newNode = new Node(newEntry);
        if(isEmpty()){
            firstNode = newNode;
        }else{
            lastNode.setNextNode(newNode);
        }
        lastNode = newNode;
    }

    public T dequeue(){
        T front = null;
        if (!isEmpty())
        {
            front = firstNode.getData();
            firstNode = firstNode.getNextNode();
            if (firstNode == null)
            {
                lastNode = null;
            }
        }else{
            throw new EmptyQueueException("Queue is Empty");
        }
        return front;
    }

    public T getFront(){
        if (!isEmpty()){
            T front = firstNode.getData();
            return front;
        }else{
            throw new EmptyQueueException("Queue is Empty");
        }
    }

    public boolean isEmpty(){
        return ((firstNode == null) && (lastNode == null));
    }

    public void clear(){
        firstNode = null;
        lastNode = null;
    }

    class Node{
        private T    data;
        private Node next;

        private Node(T dataPortion)
        {
            data = dataPortion;
            next = null;
        }

        private Node(T dataPortion, Node linkPortion)
        {
            data = dataPortion;
            next = linkPortion;
        }

        private T getData()
        {
            return data;
        }

        private void setData(T newData)
        {
            data = newData;
        }

        private Node getNextNode()
        {
            return next;
        }

        private void setNextNode(Node nextNode)
        {
            next = nextNode;
        }

    } // end Node
} // end Linkedqueue

/*
Create a queue:


isEmpty() returns true

Add to queue to get
Joe Jess Jim Jill Jane Jerry


isEmpty() returns false



Testing getFront and dequeue:

Joe is at the front of the queue.
Joe is removed from the front of the queue.

Jess is at the front of the queue.
Jess is removed from the front of the queue.

Jim is at the front of the queue.
Jim is removed from the front of the queue.

Jill is at the front of the queue.
Jill is removed from the front of the queue.

Jane is at the front of the queue.
Jane is removed from the front of the queue.

Jerry is at the front of the queue.
Jerry is removed from the front of the queue.


The queue should be empty: isEmpty() returns true


Add to queue to get
Joe Jess Jim


Testing clear:


isEmpty() returns true


Add to queue to get
Joe Jess Jim

Joe is at the front of the queue.
Joe is removed from the front of the queue.

Jess is at the front of the queue.
Jess is removed from the front of the queue.

Jim is at the front of the queue.
Jim is removed from the front of the queue.



The queue should be empty: isEmpty() returns true

The next calls will throw an exception.

Exception in thread "main" Graphs.EmptyQueueException: Queue is Empty
	at Graphs.LinkedQueue.getFront(Graphs.LinkedQueue.java:47)
	at Driver.testQueueOperations(Driver.java:76)
	at Driver.main(Driver.java:12)

Process finished with exit code 1
 */