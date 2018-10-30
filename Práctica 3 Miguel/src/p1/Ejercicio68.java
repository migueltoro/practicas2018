/**
 * @author Paco
 *
 */
package p1;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import us.lsi.common.Preconditions;

	

public class Ejercicio68 {
	

	public static Long fWhile0(Integer n) {
		Preconditions.checkArgument(n >= 0, "El argumento debe ser mayor o igual que cero");
		Aux e = Aux.create(1L,1L,2L,0);
		while (e.i < n) {
			e = e.set(2*e.a+3*e.b-e.c,e.a,e.b,e.i+1);
		}
		return e.c;
	}
	
	public static Long fRecFinal(Integer n) {
		Preconditions.checkArgument(n >= 0, "El argumento debe ser mayor o igual que cero");
		return fRecFinal(1L,1L,2L,0,n);
	}
	private static Long fRecFinal(Long a, Long b, Long c, Integer i, Integer n) {
		Long r;
		if(i==n) {
			r = c;
		} else {		
			r = fRecFinal(2*a+3*b-c,a,b,i+1,n);	
		}
		return r;
	} 
	
	public static Long fWhile1(Integer n) {
		Preconditions.checkArgument(n >= 0, "El argumento debe ser mayor o igual que cero");
		long a = 1L;  //f(i+2)
		long b = 1L;  //f(i+1)
		long c = 2L;  //f(i)
		long i = 0L;   
		while (i < n) {
			long a0 = a;
			long b0 = b;
			long c0 = c;
			a = 2 * a0 + 3 * b0 - c0;
			b = a0;
			c = b0;
			i = i+1;
		}
		return c;
	}
	

	public static Long fFuncJ11(Integer n) {
		Preconditions.checkArgument(n >= 0, "El argumento debe ser mayor o igual que cero");
		Aux initial = Aux.create(1L,1L,2L,0);
		UnaryOperator<Aux> next = x->Aux.create(2*x.a+3*x.b-x.c,x.a,x.b,x.i+1);
		return Stream.iterate(initial,next).dropWhile(x->x.i<n).findFirst().get().c;
	}

	
	public static Long fRecSinMem(Integer n) {
		Preconditions.checkArgument(n >= 0, "El argumento debe ser mayor o igual que cero");
		Long res;
		if (n == 0) {
			res = 2L;
		} else if (n <= 2) {
			res = 1L;
		} else {
			res = 2 * fRecSinMem(n - 1) + 3 * fRecSinMem(n - 2) - fRecSinMem(n - 3);
		}
		return res;
	}

	public static Long fRecConMem(Integer n) {
		Preconditions.checkArgument(n >= 0, "El argumento debe ser mayor o igual que cero");
		return fRecConMem(n, new HashMap<>());
	}
	
	private static Long fRecConMem(Integer n, Map<Integer, Long> m) {
		Long res = null;
		if (m.containsKey(n)) {
			res = m.get(n);
		} else if (n == 0) {
			res = 2L;
			m.put(n, res);
		} else if (n <= 2) {
			res = 1L;
			m.put(n, res);
		} else {
			res = 2 * fRecConMem(n - 1, m) + 3 * fRecConMem(n - 2, m) - fRecConMem(n - 3, m);
			m.put(n, res);
		}
		return res;
	}

	//*************** TEST (no se explica, sólo para profesores) **********************************************
	
	public static void main(String[] args) {
		System.out.println("Mayor valor que puede albergar Long: "+Long.MAX_VALUE+"\n");
		test(TipoAlg.WHILE, Ejercicio68::fWhile0, 100); // Comentar punteros a funciones en C
		test(TipoAlg.WHILE, Ejercicio68::fWhile1, 100); // Comentar punteros a funciones en C
		test(TipoAlg.FJ11, Ejercicio68::fFuncJ11, 100);
//		test(TipoAlg.RSM, Ejercicio68::fRecSinMem, 100);
		test(TipoAlg.RCM, Ejercicio68::fRecConMem, 100);
		test(TipoAlg.RF, Ejercicio68::fRecFinal, 100);
	} 

	private static void test(TipoAlg tipo, Function<Integer, Long> f, Integer n) {
		double t = System.currentTimeMillis();
		Long x = f.apply(n);
		t = (System.currentTimeMillis()-t);
		String sol = tipo+" para n="+n+" -> "+x+";";
		System.out.println(String.format("%-70sTiempo (segs.): %.4f", sol, t));
	}

	private static enum TipoAlg {
		WHILE, FJ11, RSM, RCM, RF;
		@Override
		public String toString() {
			String res = "Solucion Iterativa con While";
			switch (this) {
				case FJ11:
					res = "Solucion Funcional (Java 11)"; break;
				case RSM:
					res = "Solucin Recursiva Sin Memoria"; break;
				case RCM:
					res = "Solucion Recursiva Con Memoria"; break;
				case RF:
					res = "Solucion Recursiva Final";
				default:				
			}
			return res;
		}
	}
	
	public static class Aux {
		Long a = 1L;  //f(i+2)
		Long b = 1L;  //f(i+1)
		Long c = 2L;  //f(i)
		Integer i = 0;
		public Aux(Long a, Long b, Long c, Integer i) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
			this.i = i;
		} 
		public static Aux create(Long a, Long b, Long c, Integer i) {
			return new Aux(a, b, c, i);
		}
		public Aux set(Long a, Long b, Long c, Integer i) {
			this.a = a;  //f(i+2)
			this.b = b;  //f(i+1)
			this.c = c;  //f(i)
			this.i = i;
			return this;
		} 		
	}

}
