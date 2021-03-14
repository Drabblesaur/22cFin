package Graphs;//Johnny To
//22C 43Z
//3.7.2021

import java.util.Iterator;

public class DirectedGraph<T> implements GraphInterface<T>
{
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph()
    {
        vertices = new LinkedDictionary<>();
        edgeCount = 0;
    } // end default constructor


    protected void resetVertices()
    {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices


    @Override
    public QueueInterface<T> getBreadthFirstTraversal(T origin) {
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<>();
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
        VertexInterface<T> originVertex = vertices.getValue(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin); // Enqueue vertex label
        vertexQueue.enqueue(originVertex); // Enqueue vertex
        while (!vertexQueue.isEmpty())
        {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited())
                {
                    nextNeighbor.visit();
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexQueue.enqueue(nextNeighbor);
                } // end if
            } // end while
        } // end while
        return traversalOrder;
    }

    public QueueInterface<T> getDepthFirstTraversal(T origin)
    {
// Assumes graph is not empty
        resetVertices();
        QueueInterface<T> traversalOrder = new LinkedQueue<T>();
        StackInterface<VertexInterface<T>> vertexStack = new LinkedStack<>();

        VertexInterface<T> originVertex = vertices.getValue(origin);
        originVertex.visit();
        traversalOrder.enqueue(origin); // Enqueue vertex label
        vertexStack.push(originVertex); // Enqueue vertex

        while (!vertexStack.isEmpty())
        {
            VertexInterface<T> topVertex = vertexStack.peek();
            VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();

            if (nextNeighbor != null)
            {
                nextNeighbor.visit();
                traversalOrder.enqueue(nextNeighbor.getLabel());
                vertexStack.push(nextNeighbor);
            }
            else // All neighbors are visited
                vertexStack.pop();
        } // end while

        return traversalOrder;
    } // end getDepthFirstTraversal

    public StackInterface<T> getTopologicalOrder()
    {
        resetVertices();

        StackInterface<T> vertexStack = new LinkedStack<>();
        int numberOfVertices = getNumberOfVertices();
        for (int counter = 1; counter <= numberOfVertices; counter++)
        {
            VertexInterface<T> nextVertex = findTerminal();
            nextVertex.visit();
            vertexStack.push(nextVertex.getLabel());
        } // end for

        return vertexStack;
    } // end getTopologicalOrder

    /** Precondition: path is an empty stack (NOT null) */
    public int getShortestPath(T begin, T end, StackInterface<T> path)
    {
        resetVertices();
        boolean done = false;
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();

        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);

        originVertex.visit();
// Assertion: resetVertices() has executed setCost(0)
// and setPredecessor(null) for originVertex

        vertexQueue.enqueue(originVertex);

        while (!done && !vertexQueue.isEmpty())
        {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();

            Iterator<VertexInterface<T>> neighbors =
                    frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();

                if (!nextNeighbor.isVisited())
                {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                } // end if

                if (nextNeighbor.equals(endVertex))
                    done = true;
            } // end while
        } // end while

// Traversal ends; construct shortest path
        int pathLength = (int)endVertex.getCost();
        path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while

        return pathLength;
    } // end getShortestPath


    /** Precondition: path is an empty stack (NOT null) */
    public double getCheapestPath(T begin, T end, StackInterface<T> path)
    {
        resetVertices();
        boolean done = false;

// Use EntryPQ instead of Graphs.Vertex because multiple entries contain
// the same vertex but different costs - cost of path to vertex is EntryPQ's priority value
        PriorityQueueInterface<EntryPQ> priorityQueue = new HeapPriorityQueue<>();

        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);

        priorityQueue.add(new EntryPQ(originVertex, 0, null));

        while (!done && !priorityQueue.isEmpty())
        {
            EntryPQ frontEntry = priorityQueue.remove();
            VertexInterface<T> frontVertex = frontEntry.getVertex();

            if (!frontVertex.isVisited())
            {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getCost());
                frontVertex.setPredecessor(frontEntry.getPredecessor());

                if (frontVertex.equals(endVertex))
                    done = true;
                else
                {
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    Iterator<Double> edgeWeights = frontVertex.getWeightIterator();
                    while (neighbors.hasNext())
                    {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        Double weightOfEdgeToNeighbor = edgeWeights.next();

                        if (!nextNeighbor.isVisited())
                        {
                            double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
                            priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        } // end if
                    } // end while
                } // end if
            } // end if
        } // end while

// Traversal ends, construct cheapest path
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while

        return pathCost;
    } // end getCheapestPath

    protected VertexInterface<T> findTerminal()
    {
        boolean found = false;
        VertexInterface<T> result = null;

        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

        while (!found && vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();

// If nextVertex is unvisited AND has only visited neighbors)
            if (!nextVertex.isVisited())
            {
                if (nextVertex.getUnvisitedNeighbor() == null )
                {
                    found = true;
                    result = nextVertex;
                } // end if
            } // end if
        } // end while

        return result;
    } // end findTerminal

    // Used for testing
    public void displayEdges()
    {
        System.out.println("\nEdges exist from the first vertex in each line to the other vertices in the line.");
        System.out.println("(Edge weights are given; weights are zero for unweighted graphs):\n");
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext())
        {
            ((Vertex<T>)(vertexIterator.next())).display();
        } // end while
    } // end displayEdges

    @Override
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome= vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null;
    }

    public boolean removeVertex(T vertexLabel){
        if(vertices.contains(vertexLabel)){
            VertexInterface<T> removedVertex = vertices.getValue(vertexLabel);
            Iterator<VertexInterface<T>> Neighbors = vertices.getValue(vertexLabel).getNeighborIterator();
            while(Neighbors.hasNext()){
                removeEdgewithVertex(removedVertex,Neighbors.next());
            }
            vertices.remove(vertexLabel);
            return true;
        }else
            return false;
    }

    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex!= null)&&(endVertex!=null))
            result = beginVertex.connect(endVertex,edgeWeight);
        if (result)
            edgeCount++;

        return result;
    }

    @Override
    public boolean addEdge(T begin, T end) {
        return false;
    }

    @Override
    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex!= null)&&(endVertex!=null))
        {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while(!found && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            }
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.getSize();
    }

    @Override
    public int getNumberOfEdges() {
        return edgeCount;
    }

    @Override
    public void clear() {
        vertices.clear();
        edgeCount = 0;
    }

    //NEW METHODS
    public ListWithIteratorInterface<VertexInterface> getNeighbors(T vertexLabel){
        Iterator<VertexInterface<T>> neighbors =  vertices.getValue(vertexLabel).getNeighborIterator();
        ListWithIteratorInterface<VertexInterface> neighborsList = new LinkedListWithIterator<VertexInterface>();
        while(neighbors.hasNext()){
            neighborsList.add(neighbors.next());
        }
        return neighborsList;
    }
    public boolean removeEdge(T begin, T end){
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex!= null)&&(endVertex!=null))
            endVertex.remove(beginVertex);
            result = beginVertex.remove(endVertex);
        if (result)
            edgeCount++;

        return result;
    }

    private boolean removeEdgewithVertex(VertexInterface<T> beginVertex, VertexInterface<T> endVertex){
        boolean result = false;
        if ((beginVertex!= null)&&(endVertex!=null))
            endVertex.remove(beginVertex);
        result = beginVertex.remove(endVertex);
        if (result)
            edgeCount++;

        return result;
    }

    private class EntryPQ implements Comparable<EntryPQ>
    {
        private VertexInterface<T> vertex;
        private VertexInterface<T> previousVertex;
        private double cost; // cost to nextVertex

        private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex)
        {
            this.vertex = vertex;
            this.previousVertex = previousVertex;
            this.cost = cost;
        } // end constructor

        public VertexInterface<T> getVertex()
        {
            return vertex;
        } // end getVertex

        public VertexInterface<T> getPredecessor()
        {
            return previousVertex;
        } // end getPredecessor

        public double getCost()
        {
            return cost;
        } // end getCost

        public int compareTo(EntryPQ otherEntry)
        {
// Using opposite of reality since our priority queue uses a maxHeap;
// could revise using a minheap
            return (int)Math.signum(otherEntry.cost - cost);
        } // end compareTo

        public String toString()
        {
            return vertex.toString() + " " + cost;
        } // end toString
    } // end EntryPQ
} // end Graphs.DirectedGraph

/*
Testing the directed, weighted graph in Figure 28-18a.

Edges exist from the first vertex in each line to the other vertices in the line.
(Edge weights are given; weights are zero for unweighted graphs):

I F 1.0
H I 1.0
G H 1.0
F C 4.0 H 3.0
E F 3.0 H 6.0
D G 2.0
C B 3.0
B E 1.0
A B 2.0 D 5.0 E 4.0

Number of vertices = 9 (should be 9)
Number of edges = 13 (should be 13)
Edges are OK.
-------------------------------------------------------


Breadth-First Traversal beginning at vertex A:
A B D E G F H C I  Actual
A B D E G F H C I  Expected
-------------------------------------------------------


Depth-First Traversal beginning at vertex A:
A B E F C H I D G  Actual
A B E F C H I D G  Expected
-------------------------------------------------------

Finding the cheapest path in the graph in Figure 28-18a:

The cheapest path from A to B is
A B
and has a cost of 2.0.

The cheapest path from A to C is
A B E F C
and has a cost of 10.0.

The cheapest path from A to D is
A D
and has a cost of 5.0.

The cheapest path from A to E is
A B E
and has a cost of 3.0.

The cheapest path from A to F is
A B E F
and has a cost of 6.0.

The cheapest path from A to G is
A D G
and has a cost of 7.0.

The cheapest path from A to H is
A D G H
and has a cost of 8.0.

The cheapest path from A to I is
A D G H I
and has a cost of 9.0.

Done.

Process finished with exit code 0

 */