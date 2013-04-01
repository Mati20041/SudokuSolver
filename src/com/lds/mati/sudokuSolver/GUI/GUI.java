package com.lds.mati.sudokuSolver.GUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.lds.mati.CSP.engine.CSPSolver;
import com.lds.mati.CSP.engine.Coinstraint;
import com.lds.mati.CSP.engine.ConstraintsProblem;
import com.lds.mati.sudokuSolver.sudoku.SudokuProblem;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

public class GUI extends JFrame {

	private static final long serialVersionUID = -2999589277841511588L;

	private JPanel contentPane;

	private final String[] possibilities = { "", "1", "2", "3", "4", "5", "6",
			"7", "8", "9" };
	@SuppressWarnings("rawtypes")
	private JComboBox[] sudoku;
	/**
	 * @wbp.nonvisual location=42,329
	 */
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JRadioButton rdbtnNewRadioButton;

	private JButton btnSprawd;

	private JMenuItem mntmatwy;

	private JMenuItem mntmredni;

	private JMenuItem mntmTrudny;

	private JButton btnRozwi;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GUI() {
		setTitle("SUDOKU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 486, 413);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnGraj = new JMenu("Graj");
		menuBar.add(mnGraj);

		mntmatwy = new JMenuItem("\u0141atwy");
		mntmatwy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtons(false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						game(0);
						setButtons(true);
					}
				}).start();
			}
		});
		mnGraj.add(mntmatwy);

		mntmredni = new JMenuItem("\u015Aredni");
		mntmredni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtons(false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						game(1);
						setButtons(true);
					}
				}).start();
			}
		});
		mnGraj.add(mntmredni);

		mntmTrudny = new JMenuItem("Trudny");
		mntmTrudny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtons(false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						game(2);
						setButtons(true);
					}
				}).start();
			}
		});
		mnGraj.add(mntmTrudny);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 3, 5, 5));

		sudoku = new JComboBox[81];
		JPanel[] temp = new JPanel[9];
		for (int i = 0; i < 9; ++i) {
			temp[i] = new JPanel();
			temp[i].setLayout(new GridLayout(3, 3, 5, 5));
			temp[i].setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(temp[i]);
		}
		for (int i = 0; i <= sudoku.length; ++i) {
			int box = 0;
			if (i % 9 < 3)
				box = 0;
			else if (i % 9 < 6)
				box = 1;
			else if (i % 9 < 9)
				box = 2;
			if (i / 9 > 2) {
				if (i / 9 < 6)
					box += 3;
				else
					box += 6;
			}
			if (i < 81) {
				sudoku[i] = new JComboBox(possibilities);
				((JLabel) sudoku[i].getRenderer())
						.setHorizontalAlignment(SwingConstants.CENTER);
				temp[box].add(sudoku[i]);
			}
		}

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		btnRozwi = new JButton("Rozwi\u0105\u017C");
		btnRozwi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setButtons(false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						if (check())
							solve();
						else
							JOptionPane.showMessageDialog(GUI.this,
									"Pogwa³cenie ograniczeñ!");
						setButtons(true);
					}
				}).start();
			}
		});
		panel_1.add(btnRozwi);

		btnSprawd = new JButton("Sprawd\u017A");
		btnSprawd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtons(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (check()) {
							JOptionPane.showMessageDialog(GUI.this, "OK!");
						} else {
							JOptionPane.showMessageDialog(GUI.this,
									"Pogwa³cenie ograniczeñ!");
						}
						setButtons(true);
					}
				}).start();
			}
		});
		panel_1.add(btnSprawd);

		JButton btnWyczy = new JButton("Wyczy\u015B\u0107");
		btnWyczy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		panel_1.add(btnWyczy);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		rdbtnNewRadioButton = new JRadioButton("Backtracing");
		rdbtnNewRadioButton.setSelected(true);
		panel_2.add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(
				"Forward checking");
		rdbtnNewRadioButton_1.setSelected(true);
		panel_2.add(rdbtnNewRadioButton_1);
		buttonGroup.add(rdbtnNewRadioButton_1);
		buttonGroup.add(rdbtnNewRadioButton);
	}

	@SuppressWarnings("rawtypes")
	private void game(int mode) {
		reset();
		double probability = 0;
		switch (mode) {
		case 0:
			probability = 0.25;
			break;
		case 1:
			probability = 0.50;
			break;
		case 2:
			probability = 0.75;
			break;
		}

		JComboBox t = sudoku[(int) (Math.random() * sudoku.length)];
		t.setSelectedIndex((int) (Math.random() * 9) + 1);
		solve();
		for (int i = 0; i < sudoku.length; ++i) {
			if (Math.random() < probability) {
				sudoku[i].setSelectedIndex(0);
			}
		}

	}

	private boolean check() {
		Map<Integer, Integer> domain = getDomainFromGui();
		SudokuProblem factory = SudokuProblem.getFactory();
		Coinstraint<Integer>[] coinstraints = factory.getCoinstraints(null);
		Integer[] vertices = new Integer[81];
		for (Integer t : domain.keySet()) {
			vertices[t] = domain.get(t);
		}
		for (Integer t : domain.keySet()) {
			if (!coinstraints[t].isSatisfied(vertices))
				return false;
		}
		return true;
	}

	private void solve() {
		Map<Integer, Integer> domain = getDomainFromGui();
		SudokuProblem factory = SudokuProblem.getFactory();
		Coinstraint<Integer>[] coinstraints = factory.getCoinstraints(null);
		List<Integer>[] domains = factory.getDomain(domain);
		ConstraintsProblem<Integer> problem = new ConstraintsProblem<Integer>(
				coinstraints, domains);
		CSPSolver<Integer> solver = new CSPSolver<>();
		long start, end;

		Integer[] result;
		if (rdbtnNewRadioButton.isSelected()) {
			start = System.currentTimeMillis();
			result = solver.backTracking(problem);
			end = System.currentTimeMillis() - start;
		} else {
			start = System.currentTimeMillis();
			result = solver.forwardChecking(problem);
			end = System.currentTimeMillis() - start;
		}
		System.out.println("Czas rozwi¹zywania: " + end + " ms");
		setResultToGui(result);
	}

	@SuppressWarnings("rawtypes")
	private void setResultToGui(Integer[] result) {
		if (result != null) {
			for (int i = 0; i < sudoku.length; ++i) {
				JComboBox t = sudoku[i];
				t.setSelectedIndex(result[i]);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Brak rozwi¹zania!");
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<Integer, Integer> getDomainFromGui() {
		Map<Integer, Integer> domain = new HashMap<>();
		for (int i = 0; i < sudoku.length; ++i) {
			JComboBox t = sudoku[i];
			int j = t.getSelectedIndex();
			if (j != 0) {
				domain.put(i, t.getSelectedIndex());
			}
		}
		return domain;
	}

	@SuppressWarnings("rawtypes")
	private void reset() {
		for (int i = 0; i < sudoku.length; ++i) {
			JComboBox t = sudoku[i];
			t.setSelectedIndex(0);
		}
	}
	
	private void setButtons(boolean enabled){
		 btnSprawd.setEnabled(enabled);
		 mntmatwy.setEnabled(enabled);
		 mntmredni.setEnabled(enabled);
		 mntmTrudny.setEnabled(enabled);
		 btnRozwi.setEnabled(enabled);
	}

}
