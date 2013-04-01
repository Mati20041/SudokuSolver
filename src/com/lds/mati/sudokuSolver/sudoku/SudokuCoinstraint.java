package com.lds.mati.sudokuSolver.sudoku;

import com.lds.mati.CSP.engine.Coinstraint;

public class SudokuCoinstraint implements Coinstraint<Integer> {
	private int position;

	public SudokuCoinstraint(int position) {
		this.position = position;
	}

	@Override
	public boolean isSatisfied(Integer[] vertices) {
		int x = position % 9;
		int y = position / 9;
		int box = 0;
		if(x<3)
			box = 0;
		else if (x<6)
			box = 1;
		else if (x < 9)
			box = 2;
		if(y>2){
			if(y<6)
				box+=9;
			else
				box+=18;
		}
		for(int i = 0 ; i < 3 ; ++i){
			for(int j = 0 ; j < 3 ; ++j){
				if(i+3*box + j*9 != position){
					if(vertices[i+3*box + j*9] == vertices[position])
						return false;
				}
			}
		}
			
		for (int i = 0; i < 9; ++i) {
			if (i + y * 9 != position) {
				Integer curr = vertices[i + y * 9];
				if (curr!=null && curr == vertices[position]) {
					return false;
				}
			}
		}
		for (int i = 0; i < 9; ++i) {
			if (x + i * 9 != position) {
				Integer curr = vertices[x + i * 9];
				if (curr!=null && curr == vertices[position]) {
					return false;
				}
			}
		}
		return true;
	}
}
