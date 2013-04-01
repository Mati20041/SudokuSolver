package com.lds.mati.sudokuSolver;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.lds.mati.CSP.engine.CSPSolver;
import com.lds.mati.CSP.engine.Coinstraint;
import com.lds.mati.CSP.engine.ConstraintsProblem;
import com.lds.mati.sudokuSolver.GUI.GUI;
import com.lds.mati.sudokuSolver.sudoku.SudokuProblem;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//runSudoku();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	public static void runSudoku(){
		SudokuProblem factory = SudokuProblem.getFactory();
		Coinstraint<Integer>[] coinstraints = factory.getCoinstraints(0);
		List<Integer>[] domains = factory.getDomain(0);
		ConstraintsProblem<Integer> problem = new ConstraintsProblem<Integer>(
				coinstraints, domains);
		CSPSolver<Integer> solver = new CSPSolver<>();
		Integer[] result= solver.forwardChecking(problem);
		
		for(int i = 0 ; i < 81 ; ++i){
			if(i!=0&&i%3==0)
				System.out.print(" ");
			if(i!=0&&i%9 == 0)
				System.out.println();
			System.out.print(result[i]);
		}
	}
}
