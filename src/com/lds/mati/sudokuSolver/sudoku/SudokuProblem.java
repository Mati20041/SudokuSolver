package com.lds.mati.sudokuSolver.sudoku;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lds.mati.CSP.engine.Coinstraint;
import com.lds.mati.CSP.engine.DomainCoinstrantFactory;

public class SudokuProblem implements DomainCoinstrantFactory<Integer> {
	private static SudokuProblem singleton;

	private SudokuProblem() {
	}

	public static SudokuProblem getFactory() {
		if (singleton == null)
			singleton = new SudokuProblem();
		return singleton;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer>[] getDomain(Object arg) {
		Map<Integer, Integer> domain = null;
		if (arg != null)
			domain = (Map<Integer, Integer>) arg;

		List<Integer>[] domains = (List<Integer>[]) Array.newInstance(
				List.class, 81);
		for (int i = 0; i < domains.length; ++i) {
			List<Integer> tempDom = new ArrayList<>(81);
			if (domain!=null&&domain.containsKey(i)) {
				tempDom.add(domain.get(i));
			} else {
				for (int j = 1; j < 10; ++j) {
					tempDom.add(j);
				}
			}
			domains[i] = tempDom;
		}
		return domains;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Coinstraint<Integer>[] getCoinstraints(Object arg) {
		Coinstraint<Integer>[] coinstraints = (Coinstraint<Integer>[]) Array
				.newInstance(Coinstraint.class, 81);
		for (int i = 0; i < 81; ++i) {
			coinstraints[i] = new SudokuCoinstraint(i);
		}
		return coinstraints;
	}

}
