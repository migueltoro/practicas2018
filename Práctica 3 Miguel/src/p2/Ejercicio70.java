package p2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Ejercicio70 {
	
	// SOLUCION RECURSIVA SIN MEMORIA
	public static Long combiRecSinMem(Integer n, Integer k) {
		Long res = null;
		if (k == 0 || k == n) {
			res = 1L;
		} else if (k == 1 || k == n - 1) {
			res = n.longValue();
		} else {
			res = combiRecSinMem(n - 1, k - 1) + combiRecSinMem(n - 1, k);
		}
		return res;
	}

	public static Long combiRecConMem(Integer n, Integer k) {
		return combiRecConMem(n, k, new HashMap<>());
	}

	
	// SOLUCION RECURSIVA CON MEMORIA
	private static Long combiRecConMem(Integer n, Integer k, Map<List<Integer>, Long> m) {
		Long res = null;
		List<Integer> key = List.of(n, k);
		if (m.containsKey(key)) {
			res = m.get(key);
		} else if (k == 0 || k == n) {
			res = 1L;
			m.put(key, res);
		} else if (k == 1 || k == n - 1) {
			res = n.longValue();
			m.put(key, res);
		} else {
			res = combiRecConMem(n - 1, k - 1, m) + combiRecConMem(n - 1, k, m);
			m.put(key, res);
		}
		return res;
	}

	
	//*************** TEST (no se explica, solo para profesores) **********************************************
	
	public static void main(String[] args) {
		test(TipoAlg.RSM, Ejercicio70::combiRecSinMem, 30, 15);
		test(TipoAlg.RCM, Ejercicio70::combiRecConMem, 30, 15);
	}

	private static void test(TipoAlg tipo, BiFunction<Integer, Integer, Long> f, Integer n, Integer k) {
		double t = System.currentTimeMillis();
		Long x = f.apply(n,k);
		t = (System.currentTimeMillis()-t)/1000.0;
		String sol = tipo+" para (n,k)=("+n+","+k+") -> "+x+";";
		System.out.println(String.format("%-70sTiempo (segs.): %.4f", sol, t));
	}

	private static enum TipoAlg {
		RSM, RCM;
		@Override
		public String toString() {
			String res = "Solucion Recursiva Sin Memoria";
			if (this.equals(RCM)) {
				res = "Solucion Recursiva Con Memoria";
			}
			return res;
		}
	}

}
