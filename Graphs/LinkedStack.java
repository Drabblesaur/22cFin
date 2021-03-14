package Graphs;

/*
Johnny To
22C 43Z
2.1.2021
 */
public final class LinkedStack<T> implements StackInterface<T>
{

    private Node topNode; // References the first node in the chain

    public LinkedStack ()
    {
        topNode = null;
    }

    public void push (T newEntry)
    {
        Node newNode = new Node (newEntry, topNode);
        topNode = newNode;
    }

    public T peek ()
    {
        if (!isEmpty()) {
            T top = null;
            if (topNode != null)
                top = topNode.getData();
            return top;
        }else
            throw new EmptyStackException("EMPTY STACK");
    }

    public T pop ()
    {
        if (!isEmpty()){
            T top = peek ();
            if (topNode != null)
                topNode = topNode.getNextNode ();
            return top;
        }else
            throw new EmptyStackException("EMPTY STACK");
    }

    public boolean isEmpty ()
    {
        return topNode == null;
    }

    public void clear ()
    {
        topNode = null;
    }

    private class Node {

        private T data;
        private Node next;

        private Node(T dataPortion)
        {
            this.data = dataPortion;
            this.next = null;
        }

        private Node(T dataPortion, Node nextNode)
        {
            data = dataPortion;
            next = nextNode;
        }
        private Node getNextNode (){
            return next;
        }
        private T getData (){
            return data;
        }
    }

} // end Graphs.LinkedStack

/*
Create a stack:
isEmpty() returns true

Add to stack to get
Joe Jane Jill Jess Jim

isEmpty() returns false

Testing peek and pop:

Joe is at the top of the stack.
Joe is removed from the stack.

Jane is at the top of the stack.
Jane is removed from the stack.

Jill is at the top of the stack.
Jill is removed from the stack.

Jess is at the top of the stack.
Jess is removed from the stack.

Jim is at the top of the stack.
Jim is removed from the stack.

The stack should be empty: isEmpty() returns true

Add to stack to get
Jim Jess Joe


Testing clear:
The stack should be empty:

isEmpty() returns true

 myStack.peek() returns
Exception in thread "main" Graphs.EmptyStackException: EMPTY STACK
	at Graphs.LinkedStack.peek(Graphs.LinkedStack.java:38)
	at Driver.testStackOperations(Driver.java:58)
	at Driver.main(Driver.java:12)

Process finished with exit code 1
 */