package com.malte_mueller.dokobo;

import com.malte_mueller.dokobo.model.Game;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModelUnitTest {
    @Test
    public void soloTest(){
        boolean[] winners = {true, false, false, false};
        Game g = new Game(42, winners);
        assertEquals(0, g.getSolist());

        boolean[] winners2 = {false, true, false, false};
        Game g2 = new Game(42, winners2);
        assertEquals(1, g2.getSolist());

        boolean[] winners3 = {true, true, true, false};
        Game g3 = new Game(42, winners3);
        assertEquals(3, g3.getSolist());
    }
}