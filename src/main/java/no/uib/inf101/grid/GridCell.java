package no.uib.inf101.grid;


/**
 * A GridCell consists of its position and a value.
 *
 * @param value the value of the GridCell
 * @param pos the position of the GridCell
 */
public record GridCell<E>(CellPosition pos, E value) { }
