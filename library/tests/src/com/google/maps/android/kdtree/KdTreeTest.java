package com.google.maps.android.kdtree;

import android.util.Log;

import com.google.maps.android.geometry.Bounds;
import com.google.maps.android.geometry.Point;

import junit.framework.TestCase;

import java.util.Collection;

public class KdTreeTest extends TestCase {

    private KdTree<Item> mTree;

    public void testAddOnePoint() {
        Item[] item = { new Item(0,0) };
        mTree = new KdTree<Item>(item);
        Collection<Item> items = searchAll();
        assertEquals(1, items.size());
    }

    public void testEmpty() {
        mTree = new KdTree<Item>(null);
        Collection<Item> items = searchAll();
        assertEquals(0, items.size());
    }

    public void testMultiplePoints() {

        Item item1 = new Item(0, 0);
        Item item2 = new Item(.1, .1);
        Item item3 = new Item(.2, .2);

        Item[] input = { item1, item2, item3 };
        mTree = new KdTree<Item>(input);

        Collection<Item> items = searchAll();
        assertEquals(3, items.size());

        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
        assertTrue(items.contains(item3));
    }

    public void testSameLocationDifferentPoint() {
        Item[] input = {
                new Item(0,0),
                new Item(0,0)
        };
        mTree = new KdTree<Item>(input);

        assertEquals(2, searchAll().size());
    }

    public void testSearch() {
        Item[] input = new Item[10000];
        for (int i = 0; i < 10000; i++) {
            input[i] = new Item(i / 20000.0, i / 20000.0);
        }
        mTree = new KdTree<Item>(input);

        assertEquals(10000, input.length);
        assertEquals(10000, searchAll().size());
        assertEquals(1, mTree.search(new Bounds((double) 0, 0.00001, (double) 0, 0.00001)).size());
        assertEquals(0, mTree.search(new Bounds(.7, .8, .7, .8)).size());
    }

    public void testFourPoints() {
        Item[] input = {
                new Item(0.2, 0.2),
                new Item(0.7, 0.2),
                new Item(0.2, 0.7),
                new Item(0.7, 0.7)
        };
        mTree = new KdTree<Item>(input);

        assertEquals(2, mTree.search(new Bounds(0.0, 0.5, 0.0, 1.0)).size());
    }

    /**
     * Tests 400,000 points relatively uniformly distributed across the space.
     * Timing results are averaged.
     */
    /*
    public void testManyPoints() {

        Item[] input = new Item[400000];
        for (double i=0; i < 200; i++) {
            for (double j=0; j < 2000; j++) {
                input[((int) ((i * 200) + j))] = new Item(i/200.0, j/2000.0);
            }
        }
        mTree = new KdTree<Item>(input);

        // searching bounds that are exact subtrees of the main quadTree
        assertEquals(400000, searchAll().size());
        assertEquals(100000, mTree.search(new Bounds(0, .5, 0, .5)).size());
        assertEquals(100000, mTree.search(new Bounds(.5, 1, 0, .5)).size());
        assertEquals(25000, mTree.search(new Bounds(0, .25, 0, .25)).size());
        assertEquals(25000, mTree.search(new Bounds(.75, 1, .75, 1)).size());

        // searching bounds that do not line up with main quadTree
        assertEquals(399600, mTree.search(new Bounds(0, 0.999, 0, 0.999)).size());
        assertEquals(4000, mTree.search(new Bounds(0.8, 0.9, 0.8, 0.9)).size());
        assertEquals(4000, mTree.search(new Bounds(0, 1, 0, 0.01)).size());
        assertEquals(16000, mTree.search(new Bounds(0.4, 0.6, 0.4, 0.6)).size());

        // searching bounds that are small / have very exact end points
        assertEquals(1, mTree.search(new Bounds(0, .001, 0, .0001)).size());
        assertEquals(26574, mTree.search(new Bounds(0.356, 0.574, 0.678, 0.987)).size());
        assertEquals(44622, mTree.search(new Bounds(0.123, 0.456, 0.456, 0.789)).size());
        assertEquals(4884, mTree.search(new Bounds(0.111, 0.222, 0.333, 0.444)).size());

    }
    */

    private Collection<Item> searchAll() {
        return mTree.search(new Bounds(0, 1, 0, 1));
    }

    private static class Item implements KdTree.Item {
        private final Point mPoint;

        private Item(double x, double y) {
            this.mPoint = new Point(x, y);
        }

        @Override
        public Point getPoint() {
            return mPoint;
        }
    }
}
