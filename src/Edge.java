/**
 * This Edge class is not my implementation and was written by the
 * instructors of CIS121. See below
 * 
 * @author Max Scheiber (scheiber), Lewis Ellis (ellisl), 15sp
 */
public class Edge implements Comparable<Edge> {
    public final int u;
    public final int v;
    public final int weight;

    public Edge(int u, int v, int weight) {
        if (u > v) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Edge)) {
            return false;
        }
        Edge that = (Edge) other;
        return this.u == that.u && this.v == that.v
                && this.weight == that.weight;
    }

    @Override
    public int compareTo(Edge other) {
        if (weight < other.weight) {
            return -1;
        } else if (weight > other.weight) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (u ^ (u >>> 32));
        result = prime * result + (int) (v ^ (v >>> 32));
        result = prime * result + (int) (weight ^ (weight >>> 32));
        return result;
    }
}
