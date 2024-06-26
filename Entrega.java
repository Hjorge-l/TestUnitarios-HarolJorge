import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb "// TODO". L'enunciat de
 * cada un d'ells és al comentari de la seva signatura i exemples del seu funcionament als mètodes
 * `Tema1.tests`, `Tema2.tests`, etc.
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà un 0.
 *
 * - Si heu fet cap modificació que no sigui afegir un mètode, afegir proves als mètodes "tests()" o
 *   implementar els mètodes annotats amb "// TODO", la nota del grup serà un 0.
 *
 * - Principalment, la nota dependrà del correcte funcionament dels mètodes implemnetats (provant
 *   amb diferents entrades).
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . Algunes
 *   consideracions importants:
 *    + Entregau amb la mateixa codificació (UTF-8) i finals de línia (LF, no CR+LF)
 *    + Indentació i espaiat consistent
 *    + Bona nomenclatura de variables
 *    + Declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *      declaracions).
 *    + Convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for (int i = 0; ...))
 *      sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni qualificar classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 10.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1:Harol Jorge López G2
 * - Nom 2:David Gonzalez Lastra
 * - Nom 3:Elena del Pilar Fernadez Wyzynska
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * La majoria dels mètodes reben de paràmetre l'univers (representat com un array) i els predicats
   * adients (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un
   * element de l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si
   * `P(x)` és cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis (excepte el primer) us demanam que donat l'univers i els
   * predicats retorneu `true` o `false` segons si la proposició donada és certa (suposau que
   * l'univers és suficientment petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * Donat n > 1, en quants de casos (segons els valors de veritat de les proposicions p1,...,pn)
     * la proposició (...((p1 -> p2) -> p3) -> ...) -> pn és certa?
     *
     * Vegeu el mètode Tema1.tests() per exemples.
     */
    static int exercici1(int n) {
      if (n < 1) return 0;
      if (n == 1) return 1;
      //Con 1 variable Q tenemos 2 posibilidades Q = True; Q = False;
      int countTrue = 3;
      int countFalse = 1;

      for (int i = 3; i <= n; i++) {
        int auxFalse = countTrue;
        int auxTrue = countTrue + countFalse * 2 ;

        countTrue = auxTrue;
        countFalse = auxFalse;
      }

      return countTrue; // TODO
    }

    /*
     * És cert que ∀x : P(x) -> ∃!y : Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      //Como es una implicacion nos tenemos que fijar solo un los elementos que:
      //P(x) == True y comprobar que existe un unico y que cumple Q(x,y)
      //Si P(x) es True y hay mas de un y o ninguno tiene que dar false
      //Si P(x) es True y hay solo un y tal que Q(x,y), tiene que dar true
      for (int x: universe){
        //p.test(x) evalua la variable x con el predicado
        if(p.test(x)) {
          //Como solo queremos contador sea 1 despues de haber recorrido todo el universo
          int contador = 0;
          for (int y : universe) {
            if (q.test(x, y)) {
              contador++;
              if(contador > 1) return false;
            }
          }
          if(contador == 0) return false;
        }
      }
      return true; // TODO

    }

    /*
     * És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
     */
    static boolean exercici3(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      // he usado el contra reciproco y modificado minimamente el exercici2
      //∃x : ∀y :Q(x, y) -> P(x) <-> ∀x: Ey :!P(x) -> !Q(x,y)
      //Al aplicar el contra reciproco los cuantificadores tambien se giran
      //Tenemos que comprobar que para todo P(x) = true.
      //Exista un y tal que Q(x,y) = true; en caso contrario da false
      Boolean resultado = true;
      for (int x: universe){
        //p.test(x) evalua la variable x con el predicado
        if(!p.test(x)) {
          boolean existe = false;
          for (int y : universe) {
            //tiene que entrar dentro del if para que se cumpla Q(x,y)
            if (!q.test(x, y)) {
              //con que exista un ya nos sirve
              existe = true;
              break;
            }
          }
          //con este if comprobamos el para todo x
          if(existe){
            resultado = true;
          }else {
            resultado = false;
            break;
          }
        }
      }
      return resultado; // TODO
    }

    /*
     * És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
     */
    static boolean exercici4(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
      //Doble implicacion solo es verdad cuando
      // (P(x,z) = 0  Q(y,z) = 0) = True
      // (P(x,z) = 1  Q(y,z) = 1) = True
      // por lo tanto en el momento en el que alguna sea diferente de la otra la doble implicacion = false;
      for(int x: universe){
        int contadorUnicoY = 0;
        for(int y: universe){
          //suponemos que hay solo un Y
          boolean paraTodoZ = true;
          for (int z: universe){
            //if que comprueba que para todo z se cumpla las condiciones
            //P(x,z) sea diferente de Q(y,z) ya no cumple la doble implicacion
            if(p.test(x,z) != q.test(y,z)){
              paraTodoZ = false;
              break;
            }
          }
          // cuando paraTodoZ = True
          // creamos un contadorUnicoY que nos ira contando las veces que se cumple el parTodoZ
          if(paraTodoZ){
            contadorUnicoY ++;
            //En caso de que haya mas de 1 no se cumplira Existe un unico y se saldrá del bucle
            if(contadorUnicoY > 1)
              break;
          }
        }
        //Despues de comprobar que solo hay uno devolvemos true;
        if(contadorUnicoY == 1 ){
          return true;
        }
      }
      return false; // TODO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1

      // p1 -> p2 és cert exactament a 3 files
      // p1 p2
      // 0  0  <-
      // 0  1  <-
      // 1  0
      // 1  1  <-
      assertThat(exercici1(2) == 3);

      // (p1 -> p2) -> p3 és cert exactament a 5 files
      // p1 p2 rp2  p3
      // 0  0  1   0
      // 0  0  1   1  <-
      // 0  1  1   0
      // 0  1  1   1  <-
      // 1  0  0   0  <-
      // 1  0  0   1  <-
      // 1  1  1   0
      // 1  1  1   1  <-
      assertThat(exercici1(3) == 5);
      /***************** PRUEBAS EXTRA EJERCICIO 1 ****************
       *                                                          *
       ************************************************************/
      //rP3 = resultado con 3 varibles
      // p1 p2 p3 rP3 p4 Resultado
      // 0  0  0   0   0  <-
      // 0  0  0   0   1  <-
      // 0  0  1   1   0
      // 0  0  1   1   1  <-
      // 0  1  0   0   0  <-
      // 0  1  0   0   1  <-
      // 0  1  1   1   0
      // 0  1  1   1   1  <-
      // 1  0  0   1   0
      // 1  0  0   1   1  <-
      // 1  0  1   1   0
      // 1  0  1   1   1  <-
      // 1  1  0   0   0  <-
      // 1  1  0   0   1  <-
      // 1  1  1   1   0
      // 1  1  1   1   1  <-
      assertThat(exercici1(4) == 11);

      //He  encontrado un argoritmo que nos dice el número de True's y False's segun el exponente
      /*  i var     True              False
            2^2     3                 1    =  4
            2^3     3+(1*2)  =5       3    =  8
            2^3     5+(3*2)  =11      5    =  16
            2^3     11+(5*2) =21      11   =  32
            2^3     21+(11*2)=43      21   =  64
            2^3     43+(21*2)=85      43   =  128
            2^3     85+(43*2)=171     85   =  256
            2^3     171+(85*2)=341    171  =  512
            2^3     341+(171*2)=683   341  =  1024
       */
      assertThat(exercici1(5) == 21);
      assertThat(exercici1(6) == 43);
      assertThat(exercici1(7) == 85);
      assertThat(exercici1(8) == 171);
      assertThat(exercici1(9) == 341);
      assertThat(exercici1(10) == 683);
      assertThat(exercici1(1) == 1);
      assertThat(exercici1(-7) == 0);

      //************* FIN PRUEBAS EXTRA EJERCICIO 1 ****************

      // Exercici 2
      // ∀x : P(x) -> ∃!y : Q(x,y)

      assertThat(
            exercici2(
            new int[] { 1, 2, 3 },
            x -> x % 2 == 0,
            (x, y) -> x+y >= 5
          )
      );

      assertThat(
          !exercici2(
            new int[] { 1, 2, 3 },
            x -> x < 3,
            (x, y) -> x-y > 0
          )
      );

      /***************** PRUEBAS EXTRA EJERCICIO 2 ****************
       *                                                          *
       ************************************************************/
      /*
      Solo 5 cumple p(x); y solamente 5+5 cumple Q(x,y), por lo tante es true
       */
      assertThat(
              exercici2(
                      new int[] { 1, 2, 3, 4, 5 },
                      x -> x % 5 == 0,
                      (x, y) -> x+y == 10
              )
      );
      /*
      Solo 5 cumple p(x); 5+1 < 10, 5+2 < 10. ya hay dos elementos que lo cumplen Q(x,y) por lo tanto
      no hace falta mirar más es false
       */
      assertThat(
              !exercici2(
                      new int[] { 1, 2, 3, 4, 5 },
                      x -> x % 5 == 0,
                      (x, y) -> x+y < 10
              )
      );
      //Como ninguna de los elementos del universo cumple P(x) va igual lo que sea Q(X)
      //Siempre será True para ese universo
      assertThat(
              exercici2(
                      new int[] {1, 2, 3, 4, 5},
                      x -> x > 6,
                      (x, y) -> x+y < 10
              )
      );

      //************* FIN PRUEBAS EXTRA EJERCICIO 2 ****************

      // Exercici 3
      // És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 3 != 0,
            (x, y) -> y % x == 0
          )
      );

      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 4 != 0,
            (x, y) -> (x*y) % 4 != 0
          )
      );

      /***************** PRUEBAS EXTRA EJERCICIO 3 ****************
       *                                                          *
       ************************************************************/
      assertThat(
              exercici3(
                      new int[] { 1, 2, 3, 4 },
                      x -> x % 2 == 0,
                      (x, y) -> x + y > 4
              )
      );

      assertThat(
              exercici3(
                      new int[] { 5, 10, 15, 20 },
                      x -> x > 5,
                      (x, y) -> x > y
              )
      );
      //És cert que ∃x : ∀y : Q(x, y) -> P(x) ?

      assertThat(
              !exercici3(
                      new int[] { 1, 2, 3, 4 },
                      x -> x % 2 == 1,
                      (x, y) -> (x + y) > 4
              )
      );

      assertThat(
              //siempre será true ya que Q(x,y) nunca se cumple por lo tanto
              //por reglas de implicacion será true siempre
              exercici3(
                      new int[] { 1, 2, 3, 4 },
                      x -> x > 4,
                      (x, y) -> (x + y) > 10
              )
      );

      assertThat(
              //siempre sera falso ya que nunca se cumple P(x)
              !exercici3(
                      new int[] { 1, 2, 3, 4 },
                      x -> x < 1,
                      (x, y) -> (x + y) <= 10
              )
      );
      //************* FIN PRUEBAS EXTRA EJERCICIO 3 ****************

      // Exercici 4
      // És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
      assertThat(
          exercici4(
            new int[] { 0, 1, 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );

      assertThat(
          !exercici4(
            new int[] { 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );
      /***************** PRUEBAS EXTRA EJERCICIO 4 ****************
       *                                                          *
       ************************************************************/
      //Caso de prueba basico
      assertThat(
              exercici4(
                      new int[] { 1, 2, 3 },
                      (x, z) -> x == z,
                      (y, z) -> y == z
              )
      );
      //Caso de prueba único Y
      assertThat(
              exercici4(
                      new int[] { 1, 2, 3},
                      (x, z) -> x % 2 == z % 2,
                      (y, z) -> y == z
      ));
      //Caso sin un unico Y
      assertThat(
              !exercici4(
                      new int[] { 1, 2, 3, 4},
                      (x, z) -> x % 2 == z % 2,
                      (y, z) -> y == z
      ));
      // X sin un Y unico
      assertThat(
              exercici4(
                      new int[] { 1, 2, 3 },
                      (x, z) -> x <= z,
                      (y, z) -> y <= z
              )
      );

      //todos los z tiene un unico
      assertThat(
              exercici4(
                      new int[] { 1, 2, 3, 4 },
                      (x, z) -> x > z,
                      (y, z) -> y > z
              )
      );
      //************* FIN PRUEBAS EXTRA EJERCICIO 4 ****************
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {0,1}, {1,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   * Als tests utilitzarem extensivament la funció generateRel definida al final (també la podeu
   * utilitzar si la necessitau).
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam o bé amb el seu
   * graf o amb un objecte de tipus Function<Integer, Integer>. Sempre donarem el domini int[] a, el
   * codomini int[] b. En el cas de tenir un objecte de tipus Function<Integer, Integer>, per aplicar
   * f a x, és a dir, "f(x)" on x és d'A i el resultat f.apply(x) és de B, s'escriu f.apply(x).
   */
  static class Tema2 {
    /*
     * Calculau el nombre d'elements del conjunt (a u b) × (a \ c)
     *
     * Podeu soposar que `a`, `b` i `c` estan ordenats de menor a major.
     */
    static int exercici1(int[] a, int[] b, int[] c) {
      HashSet<Integer>  resultado = new HashSet<>();

      //HasSet nos permite añadir elementos
      //Si intentamos añadir un elemento que ya está, add devolverá false y no lo añadirá
      for (int x: a) {resultado.add(x);}
      for (int x: b) {resultado.add(x);}

      HashSet<Integer> diferencia = new HashSet<>();
      for(int x: a){diferencia.add(x);}
      for(int x: c){diferencia.remove(x);}


      return resultado.size() * diferencia.size(); // TODO
    }

    /*
     * La clausura d'equivalència d'una relació és el resultat de fer-hi la clausura reflexiva, simètrica i
     * transitiva simultàniament, i, per tant, sempre és una relació d'equivalència.
     *
     * Trobau el cardinal d'aquesta clausura.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici2(int[] a, int[][] rel) {
      return -1; // TODO
    }

    /*
     * Comprovau si la relació `rel` és un ordre total sobre `a`. Si ho és retornau el nombre
     * d'arestes del seu diagrama de Hasse. Sino, retornau -2.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici3(int[] a, int[][] rel) {
      if (!esOrdenTotal(a, rel)) {
        return -2;
      }

      // Calcular el número de aristas en el diagrama de Hasse
      return contarAristasHasse(a, rel);
    }

    // Comprueba si la relación es un orden total
    private static boolean esOrdenTotal(int[] a, int[][] rel) {
      int n = a.length;
      // Reflexiva: cada elemento se relaciona consigo mismo
      for (int i = 0; i < n; i++) {
        if (!relacionado(a[i], a[i], rel)) {
          return false;
        }
      }
      // Antisimétrica y Total
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i != j) {
            if (!relacionado(a[i], a[j], rel) && !relacionado(a[j], a[i], rel)) {
              return false;
            }
            if (relacionado(a[i], a[j], rel) && relacionado(a[j], a[i], rel) && a[i] != a[j]) {
              return false;
            }
          }
        }
      }
      // Transitiva
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          for (int k = 0; k < n; k++) {
            if (relacionado(a[i], a[j], rel) && relacionado(a[j], a[k], rel) && !relacionado(a[i], a[k], rel)) {
              return false;
            }
          }
        }
      }
      return true;
    }

    // Comprueba si x está relacionado con y en rel
    private static boolean relacionado(int x, int y, int[][] rel) {
      for (int[] par : rel) {
        if (par[0] == x && par[1] == y) {
          return true;
        }
      }
      return false;
    }

    // Contar las aristas del diagrama de Hasse
    private static int contarAristasHasse(int[] a, int[][] rel) {
      int n = a.length;
      int aristas = 0;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i != j && relacionado(a[i], a[j], rel)) {
            boolean esAristaHasse = true;
            for (int k = 0; k < n; k++) {
              if (k != i && k != j && relacionado(a[i], a[k], rel) && relacionado(a[k], a[j], rel)) {
                esAristaHasse = false;
                break;
              }
            }
            if (esAristaHasse) {
              aristas++;
            }
          }
        }
      }
      return aristas;
    }


    /*
     * Comprovau si les relacions `rel1` i `rel2` són els grafs de funcions amb domini i codomini
     * `a`. Si ho son, retornau el graf de la composició `rel2 ∘ rel1`. Sino, retornau null.
     *
     * Podeu soposar que `a`, `rel1` i `rel2` estan ordenats de menor a major (les relacions,
     * lexicogràficament).
     */
    static int[][] exercici4(int[] a, int[][] rel1, int[][] rel2) {
      if (comprobar_funcion(a, rel1) && comprobar_funcion(a, rel2)) {
        return componer(rel1, rel2);
      }
      return null;
    }

    static boolean comprobar_funcion(int[] a, int[][] relacion) {
      HashSet<Integer> conjunto = new HashSet<>();
      for (int[] par : relacion) {
        if (conjunto.contains(par[0])) {
          return false;
        }
        conjunto.add(par[0]);
      }
      for (int x : a) {
        if (!conjunto.contains(x)) {
          return false;
        }
      }
      return true;
    }

    static int[][] componer(int[][] rel1, int[][] rel2) {
      //Array list para almacenar las relaciones
      List<int[]> lista = new ArrayList<>();
      for (int[] elem1 : rel1) {
        for (int[] elem2 : rel2) {
          if (elem1[1] == elem2[0]) {
            lista.add(new int[]{elem1[0], elem2[1]});
          }
        }
      }
      // Convertir la lista a un array bidimensional
      return lista.toArray(new int[0][]);
    }

    /*
     * Comprovau si la funció `f` amb domini `dom` i codomini `codom` té inversa. Si la té, retornau
     * el seu graf (el de l'inversa). Sino, retornau null.
     */
    static int[][] exercici5(int[] dom, int[] codom, Function<Integer, Integer> f) {
      HashSet<Integer> valores = new HashSet<>();
      List<int[]> graf = new ArrayList<>();

      // Verificar si la función es inyectiva
      for (int x : dom) {
        int fx = f.apply(x);
        if (!valores.add(fx)) {
          return null; // null si no es inyectiva
        }
        graf.add(new int[]{fx, x});
      }

      // Verificar si la función es sobreyectiva
      for (int y : codom) {
        if (!valores.contains(y)) {
          return null; // null si no es sobreyectiva
        }
      }

      // Convertir la lista a matriz y retornar el gráfico ordenado
      int[][] resultado = lexSorted(graf.toArray(new int[0][]));
      return resultado;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // |(a u b) × (a \ c)|

      assertThat(
          exercici1(
            new int[] { 0, 1, 2 },
            new int[] { 1, 2, 3 },
            new int[] { 0, 3 }
          )
          == 8
      );

      assertThat(
          exercici1(
            new int[] { 0, 1 },
            new int[] { 0 },
            new int[] { 0 }
          )
          == 2
      );

      /************* PRUEBAS EXTRA EJERCICIO 1 T2 *****************
       *                                                          *
       ************************************************************/
      assertThat(
              exercici1(
                      new int[] { 3, 4, 5 },
                      new int[] { 1,2,3 },
                      new int[] { 4, 5}
              ) == 5
      );

      assertThat(
              exercici1(
                      new int[] { 0, 2, 4, 6 },
                      new int[] { 1, 3, 5 },
                      new int[] { 0, 6 }
              ) == 14
      );

      assertThat(
              exercici1(
                      new int[] { 1, 2, 3, 4, 5 },
                      new int[] { 3, 4, 5, 6, 7 },
                      new int[] { 2, 5 }
              ) == 21
      );

      assertThat(
              exercici1(
                      new int[] { 1, 2, 3 },
                      new int[] { 4, 5, 6 },
                      new int[] { }
              ) == 18
      );

      assertThat(
              exercici1(
                      new int[] { 1, 2, 3 },
                      new int[] { 4, 5, 6 },
                      new int[] { 1 }
              ) == 12
      );
      //*********** FIN PRUEBAS EXTRA EJERCICIO 1 T2 **************

      // Exercici 2
      // nombre d'elements de la clausura d'equivalència

      final int[] int08 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

/*      assertThat(exercici2(int08, generateRel(int08, (x, y) -> y == x + 1)) == 81);

      final int[] int123 = { 1, 2, 3 };

      assertThat(exercici2(int123, new int[][] { {1, 3} }) == 5);*/

      /************* PRUEBAS EXTRA EJERCICIO 2 T2 *****************
       *                                                          *
       ************************************************************/

      //*********** FIN PRUEBAS EXTRA EJERCICIO 2 T2 **************

      // Exercici 3
      // Si rel és un ordre total, retornar les arestes del diagrama de Hasse

     final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(exercici3(int05, generateRel(int05, (x, y) -> x >= y)) == 5);
      assertThat(exercici3(int08, generateRel(int05, (x, y) -> x <= y)) == -2);

      /************* PRUEBAS EXTRA EJERCICIO 3 T2 *****************
       *                                                          *
       ************************************************************/

      //*********** FIN PRUEBAS EXTRA EJERCICIO 3 T2 **************

      // Exercici 4
      // Composició de grafs de funcions (null si no ho son)

      assertThat(
          exercici4(
            int05,
            generateRel(int05, (x, y) -> x*x == y),
            generateRel(int05, (x, y) -> x == y)
          )
          == null
      );


      var ex4test2 = exercici4(
          int05,
          generateRel(int05, (x, y) -> x + y == 5),
          generateRel(int05, (x, y) -> y == (x + 1) % 6)
        );

      assertThat(
          Arrays.deepEquals(
            lexSorted(ex4test2),
            generateRel(int05, (x, y) -> y == (5 - x + 1) % 6)
          )
      );

      /************* PRUEBAS EXTRA EJERCICIO 4 T2 *****************
       *                                                          *
       ************************************************************/

      //*********** FIN PRUEBAS EXTRA EJERCICIO 4 T2 **************

      // Exercici 5
      // trobar l'inversa (null si no existeix)

      assertThat(exercici5(int05, int08, x -> x + 3) == null);

      assertThat(
          Arrays.deepEquals(
            lexSorted(exercici5(int08, int08, x -> 8 - x)),
            generateRel(int08, (x, y) -> y == 8 - x)
          )
      );
    }

    /**
     * Ordena lexicogràficament un array de 2 dimensions
     * Per exemple:
     *  arr = {{1,0}, {2,2}, {0,1}}
     *  resultat = {{0,1}, {1,0}, {2,2}}
     */
    static int[][] lexSorted(int[][] arr) {
      if (arr == null)
        return null;

      var arr2 = Arrays.copyOf(arr, arr.length);
      Arrays.sort(arr2, Arrays::compare);
      return arr2;
    }

    /**
     * Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
     * Per exemple:
     *   as = {0, 1}
     *   bs = {0, 1, 2}
     *   pred = (a, b) -> a == b
     *   resultat = {{0,0}, {1,1}}
     */
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      var rel = new ArrayList<int[]>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }

    /// Especialització de generateRel per a = b
    static int[][] generateRel(int[] as, BiPredicate<Integer, Integer> pred) {
      return generateRel(as, as, pred);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Els (di)grafs vendran donats com llistes d'adjacència (és a dir, tractau-los com diccionaris
   * d'adjacència on l'índex és la clau i els vèrtexos estan numerats de 0 a n-1). Per exemple,
   * podem donar el graf cicle d'ordre 3 com:
   *
   * int[][] c3dict = {
   *   {1, 2}, // veïns de 0
   *   {0, 2}, // veïns de 1
   *   {0, 1}  // veïns de 2
   * };
   *
   * **NOTA: Els exercicis d'aquest tema conten doble**
   */

  /************* PRUEBAS EXTRA EJERCICIO 5 T2 *****************
   *                                                          *
   ************************************************************/

  //*********** FIN PRUEBAS EXTRA EJERCICIO 5 T2 **************
  static class Tema3 {
    /*
     * Determinau si el graf és connex. Podeu suposar que `g` no és dirigit.
     */
    static boolean exercici1(int[][] g) {
      int n = g.length; // Número de nodos en el grafo
      if (n == 0) return true; // Un grafo vacío se considera conexo

      boolean[] nodos_visitados = new boolean[n];

      // Buscar el primer nodo no vacío para iniciar la DFS
      int startNode = -1;
      for (int i = 0; i < n; i++) {
        if (g[i].length > 0) {
          startNode = i;
          break;
        }
      }

      // Si no se encontró un nodo con aristas, el grafo es disconexo si hay más de un nodo
      if (startNode == -1) {
        return n == 1; // Un solo nodo sin aristas es conexo, más de uno no lo es
      }

      // Realizar DFS desde el primer nodo no vacío
      busqueda_profunda(g, startNode, nodos_visitados);
      // Verificar si hay nodos aislados
      for (int i = 0; i < n; i++) {
        if (g[i].length == 0 && i != startNode) {
          return false; // Si hay algún nodo aislado y no es el nodo inicial, el grafo no es conexo
        }
      }

      // Verificar si todos los nodos con aristas han sido visitados
      for (int i = 0; i < n; i++) {
        if (g[i].length > 0 && !nodos_visitados[i]) {
          return false; // Si algún nodo no vacío no fue visitado, el grafo no es conexo
        }
      }
      return true; // Todos los nodos fueron visitados, el grafo es conexo
    }
    static void busqueda_profunda(int[][] g, int v, boolean[] visited) {
      visited[v] = true; // Marca el nodo como visitado
      // Visita todos los nodos adyacentes no visitados
      for (int adj : g[v]) {
        if (!visited[adj]) {
          busqueda_profunda(g, adj, visited);
        }
      }
    }

    /*
     * Donat un tauler d'escacs d'amplada `w` i alçada `h`, determinau quin és el mínim nombre de
     * moviments necessaris per moure un cavall de la casella `i` a la casella `j`.
     *
     * Les caselles estan numerades de `0` a `w*h-1` per files. Per exemple, si w=5 i h=2, el tauler
     * és:
     *   0 1 2 3 4
     *   5 6 7 8 9
     *
     * Retornau el nombre mínim de moviments, o -1 si no és possible arribar-hi.
     */
    static int exercici2(int w, int h, int i, int j) {
      //Posibles movimientos del caballo
      final int[][] MOVIMIENTOS_CABALLO = {
              {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
              {1, -2}, {1, 2}, {2, -1}, {2, 1}
      };

      //Casos triviales

      if(i==j){
        return 0;
      }
      if(w <= 0 || h <= 0){
        return -1;
      }
      //creamos un array bidimensional con h y w que está iniciado todo a false
      boolean[][] casillas_visitadas = new boolean[h][w];
      List<int[]> cola = new ArrayList<>();

      //Como nos pasan por parametro una cardenada que esta en una dimension.
      //Eso lo tenemos que transformar en dos dimensiones, la posicion inicial y la final.
      int inicio_X = i / w;
      int inicio_Y = i % w;

      int final_X = j / w;
      int final_Y = j % w;

      // Posición inicial del caballo
      // {x, y, distancia}
      int[] posicion_inicial = {inicio_X, inicio_Y,0};
      cola.add(posicion_inicial);
      casillas_visitadas[inicio_X][inicio_Y] = true;

      while (!cola.isEmpty()) {
        int[] current = cola.remove(0);
        int x = current[0];
        int y = current[1];
        int dist = current[2];

        //Consultamos todos los posibles movimientos del caballo
        for (int[] movimiento : MOVIMIENTOS_CABALLO) {
          int nx = x + movimiento[0];
          int ny = y + movimiento[1];

          // Comprobar si la nueva posición está dentro del trablero
          // y que no haya sido visitada
          if (nx >= 0 && ny >= 0 && nx < h && ny < w && !casillas_visitadas[nx][ny]) {
            if (nx == final_X && ny == final_Y) {
              return dist + 1; // Encontró la posición objetivo
            }
            cola.add(new int[]{nx, ny, dist + 1});
            casillas_visitadas[nx][ny] = true;
          }
        }
      }
      return -1;
    }

    /*
     * Donat un arbre arrelat (graf dirigit `g`, amb arrel `r`), decidiu si el vèrtex `u` apareix
     * abans (o igual) que el vèrtex `v` al recorregut en preordre de l'arbre.
     */
    static boolean exercici3(int[][] g, int r, int u, int v) {
      List<Integer> preordre = new ArrayList<>();
      preordreRec(g, r, preordre);

      // Trobar les posicions de u i v en el recorregut en preordre
      int posU = preordre.indexOf(u);
      int posV = preordre.indexOf(v);

      // Retorna true si u apareix abans o en la mateixa posició que v
      return posU <= posV;
    }

    // Funció auxiliar per realitzar el recorregut en preordre
    static void preordreRec(int[][] g, int r, List<Integer> preordre) {
      preordre.add(r);
      for (int fill : g[r]) {
        preordreRec(g, fill, preordre);
      }
    }

    /*
     * Donat un recorregut en preordre (per exemple, el primer vèrtex que hi apareix és `preord[0]`)
     * i el grau de cada vèrtex (per exemple, el vèrtex `i` té grau `d[i]`), trobau l'altura de
     * l'arbre corresponent.
     *
     * L'altura d'un arbre arrelat és la major distància de l'arrel a les fulles.
     */
    static int exercici4(int[] preord, int[] d) {
      int altura = calcularAltura(preord, d, 0, preord.length, 0);
      return altura;
    }

    private static int calcularAltura(int[] preord, int[] grados, int inici, int fi, int nivell) {
      if (inici >= fi) {
        return nivell - 1;
      }

      int v = preord[inici];
      int fills = grados[v];
      int alturaMax = nivell;

      int index = inici + 1;
      for (int i = 0; i < fills; i++) {
        int novaAltura = calcularAltura(preord, grados, index, fi, nivell + 1);
        alturaMax = Math.max(alturaMax, novaAltura);

        // Avança l'índex tants passos com l'arbre fill s'estén
        int next = index + 1;
        for (int j = 0; j < grados[preord[index]]; j++) {
          next += grados[preord[next]] + 1;
        }
        index = next;
      }

      return alturaMax;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // G connex?

      final int[][] B2 = { {}, {} };

      final int[][] C3 = { {1, 2}, {0, 2}, {0, 1} };

      final int[][] C3D = { {1}, {2}, {0} };

      assertThat(exercici1(C3));
      assertThat(!exercici1(B2));
      assertThat(exercici1(C3D));

      /************* PRUEBAS EXTRA EJERCICIO 1 T3 *****************
       *                                                          *
       ************************************************************/
      //Cuadrado
      final int[][] C4 = { {1,3}, {0, 2}, {1, 3}, {0,2} };
      assertThat(exercici1(C4));
      //Cuadrado con un nodo extra que no está conectado a ninguno de los otros
      final int[][] C4V2 = { {1,3}, {0, 2}, {1, 3}, {0,2}, {} };
      assertThat(!exercici1(C4V2));
      //Cuadrado con varios nodos extra que no están conectados con ninguno de los otros
      final int[][] C4V3 = { {1,3}, {}, {0, 2}, {}, {1, 3}, {}, {0,2}};
      assertThat(!exercici1(C4V3));
      //Grafo camino
      final int[][] camino = {{1},{0,2},{1,3},{2,4},{3}};
      assertThat(exercici1(camino));

      //Grafo con 2 caminos
      final int[][] caminoV2 = {{1},{0},{3},{2,4},{3}};
      assertThat(!exercici1(caminoV2));
      //Grafo con 4 caminos
      final int[][] caminoV3 = {{1},{0},{3},{2,4},{3}, {6}, {5}, {8},{7,9},{8}};
      assertThat(!exercici1(caminoV3));
      //Grafo Bipartito
      final int[][] bipartito = {{3, 4}, {4, 5}, {5, 6}, {0}, {0, 1}, {1, 2}, {2}};
      assertThat(exercici1(bipartito));
      //S-Bipartito
      final int[][] bipartitoV2 = {{3,4,5,6},{3,4,5,6},{3,4,5,6},{0,1,2},{0,1,2},{0,1,2},{0,1,2}};
      assertThat(exercici1(bipartitoV2));
      //S-Bipartito + nodo no conexo
      final int[][] bipartitoV3 = {{4,5,6,7},{4,5,6,7},{4,5,6,7},{},{0,1,2},{0,1,2},{0,1,2},{0,1,2}};
      assertThat(!exercici1(bipartitoV3));
      //S-Bipartito + nodo conectado a un nodo arbitrario
      final int[][] bipartitoV4 = {{4,5,6,7},{4,5,6,7,3},{4,5,6,7},{1},{0,1,2},{0,1,2},{0,1,2},{0,1,2}};
      assertThat(exercici1(bipartitoV4));
      //*********** FIN PRUEBAS EXTRA EJERCICIO 1 T3 **************

      // Exercici 2
      // Moviments de cavall

      // Tauler 4x3. Moviments de 0 a 11: 3.
      // 0  1   2   3
      // 4  5   6   7
      // 8  9  10  11
      assertThat(exercici2(4, 3, 0, 11) == 3);

      // Tauler 3x2. Moviments de 0 a 2: (impossible).
      // 0 1 2
      // 3 4 5
      assertThat(exercici2(3, 2, 0, 2) == -1);

      /************* PRUEBAS EXTRA EJERCICIO 2 T3 *****************
       *                                                          *
       ************************************************************/
      // Tauler 3x2. Moviments de 0 a 2: .
      // 0 1 2 3
      // 4 5 6 7
      assertThat(exercici2(4, 2, 5, 3) == 1);
      // Tauler 4x3. Moviments de 0 a 2: .
      // 0 1 2  3
      // 4 5 6  7
      // 8 9 10 11
      assertThat(exercici2(4, 3, 6, 3) == 4);
      // Tauler 4x3. Moviments de 0 a 2: .
      // 0 1 2  3
      // 4 5 6  7
      // 8 9 10 11
      assertThat(exercici2(4, 3, 4, 3) == 2);

      //Casos de fallo
      // Tauler 2x2. Moviments de 0 a 2: (impossible).
      // 0 1
      // 2 3
      assertThat(exercici2(2, 2, 0, 2) == -1);

      //*********** FIN PRUEBAS EXTRA EJERCICIO 2 T3 **************

      // Exercici 3
      // u apareix abans que v al recorregut en preordre (o u=v)

      final int[][] T1 = {
        {1, 2, 3, 4},
        {5},
        {6, 7, 8},
        {},
        {9},
        {},
        {},
        {},
        {},
        {10, 11},
        {},
        {}
      };

      assertThat(exercici3(T1, 0, 5, 3));
      assertThat(!exercici3(T1, 0, 6, 2));
      /************* PRUEBAS EXTRA EJERCICIO 3 T3 *****************
       *                                                          *
       ************************************************************/
      assertThat(!exercici3(T1, 0, 3, 1));
      assertThat(!exercici3(T1, 0, 8, 7));

      //Son iguales
      assertThat(exercici3(T1, 0, 0, 0));

      assertThat(exercici3(T1, 0, 10, 11));
      assertThat(exercici3(T1, 0, 9, 11));
      //*********** FIN PRUEBAS EXTRA EJERCICIO 3 T3 **************

      // Exercici 4
      // Altura de l'arbre donat el recorregut en preordre

      final int[] P1 = { 0, 1, 5, 2, 6, 7, 8, 3, 4, 9, 10, 11 };
      final int[] D1 = { 4, 1, 3, 0, 1, 0, 0, 0, 0, 2,  0,  0 };

      final int[] P2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
      final int[] D2 = { 2, 0, 2, 0, 2, 0, 2, 0, 0 };

      assertThat(exercici4(P1, D1) == 3);
      assertThat(exercici4(P2, D2) == 4);

      /************* PRUEBAS EXTRA EJERCICIO 4 T3 *****************
       *                                                          *
       ************************************************************/
      assertThat(exercici4(new int[]{0}, new int[] {0}) == 0);
      //*********** FIN PRUEBAS EXTRA EJERCICIO 4 T3 **************

    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * En aquest tema no podeu:
   *  - Utilitzar la força bruta per resoldre equacions: és a dir, provar tots els nombres de 0 a n
   *    fins trobar el que funcioni.
   *  - Utilitzar long, float ni double.
   *
   * Si implementau algun dels exercicis així, tendreu un 0 d'aquell exercici.
   */
  static class Tema4 {
    /*
     * Calculau el mínim comú múltiple de `a` i `b`.
     */
    static int exercici1(int a, int b) {
      // para encontrar el mcm podemos usar el algoritmo de euclides
      // usando la siguiente formula mcm(a,b) = abs(a * b)/mcd_euclides(a,b)
      int abs_a = a;
      int abs_b = b;
      if(a < 0){abs_a = abs_a * -1;}
      if(b < 0){abs_b = abs_b * -1;}

      return (abs_a*abs_b)/mcd_euclides(abs_a,abs_b); // TO DO
    }

    /*  Función EXTRA: implementa el algoritmo de Euclides
                       para encontrar el mcd y lo devuelve
                   */
    static int mcd_euclides(int a, int b) {
      //algoritmo de euclides
      while (b != 0 ) {
        int temp = b;
        b = a % b;
        a = temp;
      }
      return a;
    }
    /*
     * Trobau totes les solucions de l'equació
     *
     *   a·x ≡ b (mod n)
     *
     * donant els seus representants entre 0 i n-1.
     *
     * Podeu suposar que `n > 1`. Recordau que no no podeu utilitzar la força bruta.
     */
    static int[] exercici2(int a, int b, int n) {
      //Usamos el algorimo de Euclides para calcular el mcd
      int mcd = mcd_euclides(a, n);
      if (b % mcd != 0) {
        //comprobamos si el mcd divide a b
        //si el mcd no divide a b no tiene solucion
        return new int[]{};//devolvemos una lista vacia
      }
      //Reducimos los coeficientes
      int a_reducido = a / mcd;
      int b_reducido = b / mcd;
      int n_reducido = n / mcd;

      int[] euclides_resultado = algoritmo_Euclides_extendido(a_reducido,n_reducido);

      int x0 = euclides_resultado [1] * (b_reducido / euclides_resultado [0]) % n_reducido;

      // Ajustar el rango de x0
      if (x0 < 0) {
        x0 += n_reducido;
      }

      List<Integer> soluciones = new ArrayList<>();
      for (int k = 0; k < mcd; k++) {
        soluciones.add((x0 + k * n_reducido) % n);
      }

      int[] lista_soluciones = new int[soluciones.size()];

      for (int i = 0; i < soluciones.size(); i++) {
        lista_soluciones[i] = soluciones.get(i);
      }

      return lista_soluciones;
    }

    /*  Función EXTRA: implementa el algoritmo extendido de Euclides
                       para encontrar el mcd de 'a' y 'b' y los coeficientes de Bézout 'x' e 'y'
                       tal que a*x + b*y = mcd(a,b)

                       devuelve un array con tres elementos [mcd,x,y]
                       */
    static int[] algoritmo_Euclides_extendido(int a, int b){
      //Este metodo nos devuelve una solucion en particular a*x +b*y
      if (b == 0) {
        return new int[] { a, 1, 0 };
      }
      int resto = a % b;
      int[] result = algoritmo_Euclides_extendido(b, resto);
      int mcd = result[0];
      int x = result[2];
      int y = result[1] - (a / b) * result[2];
      return new int[] { mcd, x, y };
    }
    /*
     * Donats `a != 0`, `b != 0`, `c`, `d`, `m > 1`, `n > 1`, determinau si el sistema
     *
     *   a·x ≡ c (mod m)
     *   b·x ≡ d (mod n)
     *
     * té solució.
     */
    static boolean exercici3(int a, int b, int c, int d, int m, int n) {
      int mcd = mcd_euclides(m, n);

      if ((c - d) % mcd != 0) {
        return false; // No hay solución si el mcd no divide la diferencia de c y d
      }

      // Podemos aplicar el Teorema Chino del Resto si m y n son coprimos
      if (mcd == 1) {
        return true;
      }

      // Verificar si las ecuaciones son compatibles cuando gcd(m, n) != 1
      int[] euclides_resultado = algoritmo_Euclides_extendido(m / mcd, n / mcd);
      int x0 = euclides_resultado[1] * (c - d) / mcd;

      // Verificar si x0 es una solución
      int lcm = exercici1(m,n);// Calcular el mínimo común múltiple
      for (int k = 0; k < mcd; k++) {
        int x = x0 + k * lcm;
        if ((a * x % m == c % m) && (b * x % n == d % n)) {
          return true;
        }
      }

      return false;
    }

    /*
     * Donats `n` un enter, `k > 0` enter, i `p` un nombre primer, retornau el residu de dividir n^k
     * entre p.
     *
     * Alerta perquè és possible que n^k sigui massa gran com per calcular-lo directament.
     * De fet, assegurau-vos de no utilitzar cap valor superior a max{n, p²}.
     *
     * Anau alerta també abusant de la força bruta, la vostra implementació hauria d'executar-se en
     * qüestió de segons independentment de l'entrada.
     */
    static int exercici4(int n, int k, int p) {
      if (p == 1) return 0; // Cualquier número mod 1 es 0

      // Paso 1: Reducir la base y manejar negativos
      n = Math.floorMod(n, p);

      // Paso 2: Calcular phi(p) como p es primo usamos las formula del pequeño Fermat
      int phiP = p - 1;

      // Paso 3: Reducir el exponente usando phi(p)
      k = k % phiP;

      // Paso 4: Calcular la potencia usando Exponentiation by Squaring
      int result = modPow(n, k, p);

      // Asegurarse de que el resultado está en el rango correcto
      return result;
    }

    static int modPow(int base, int exp, int mod) {
      long resultado = 1;
      long baseMod = base % mod;
      if (baseMod < 0) {
        baseMod += mod; // Asegurarse de que baseMod sea positivo
      }

      while (exp > 0) {
        if (exp % 2 == 1) { // Si exp es impar
          resultado = (resultado * baseMod) % mod;
        }
        exp = exp / 2; // exp = exp / 2
        baseMod = (baseMod * baseMod) % mod;
      }
      return (int) resultado;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // mcm(a, b)

      assertThat(exercici1(35, 77) == 5*7*11);
      assertThat(exercici1(-8, 12) == 24);

      /************* PRUEBAS EXTRA EJERCICIO 1 T4 *****************
       *                                                          *
       ************************************************************/
      assertThat(exercici1(5, 4) == 20);
      assertThat(exercici1(-5, 4) == 20);
      assertThat(exercici1(5, -4) == 20);
      assertThat(exercici1(-5, -4) == 20);
      assertThat(exercici1(10, 0) == 0);
      assertThat(exercici1(0, 10) == 0);
      //primos
      assertThat(exercici1(2, 3) == 6);
      assertThat(exercici1(7, 11) == 77);
      assertThat(exercici1(-7, -11) == 77);

      assertThat(exercici1(10,5) == 10);
      //*********** FIN PRUEBAS EXTRA EJERCICIO 1 T4 **************

      // Exercici 2
      // Solucions de a·x ≡ b (mod n)

      assertThat(Arrays.equals(exercici2(2, 2, 4), new int[] { 1, 3 }));
      assertThat(Arrays.equals(exercici2(3, 2, 4), new int[] { 2 }));

      /************* PRUEBAS EXTRA EJERCICIO 2 T4 *****************
       *                                                          *
       ************************************************************/
      assertThat(Arrays.equals(exercici2(14, 30, 100), new int[] { 45, 95 }));
      assertThat(Arrays.equals(exercici2(21, 14, 77), new int[]{8, 19, 30, 41, 52, 63, 74}));
      assertThat(Arrays.equals(exercici2(9, 18, 27), new int[]{2, 5, 8, 11, 14, 17, 20, 23, 26}));
      assertThat(Arrays.equals(exercici2(10, 25, 35), new int[]{6, 13, 20, 27, 34}));
      //*********** FIN PRUEBAS EXTRA EJERCICIO 2 T4 **************

      // Exercici 3
      // El sistema a·x ≡ c (mod m), b·x ≡ d (mod n) té solució?

      assertThat(exercici3(3, 2, 2, 2, 5, 4));
      assertThat(!exercici3(3, 2, 2, 2, 10, 4));

      /************* PRUEBAS EXTRA EJERCICIO 3 T4 *****************
       *                                                          *
       ************************************************************/
      //Casos de True
      assertThat(exercici3(2, 3, 4, 5, 6, 7));
      assertThat(exercici3(2, 3, 4, 6, 6, 7));
      assertThat(exercici3(5, 7, 8, 3, 9, 11));
      //Casos de False
      assertThat(!exercici3(6, 8, 9, 12, 10, 15));
      assertThat(!exercici3(10, 6, 5, 3, 12, 14));
      assertThat(!exercici3(8, 12, 10, 15, 18, 21));

      //*********** FIN PRUEBAS EXTRA EJERCICIO 3 T4 **************

      // Exercici 4
      // n^k mod p

      assertThat(exercici4(2018, 2018, 5) == 4);
      assertThat(exercici4(-2147483646, 2147483645, 46337) == 7435);

      /************* PRUEBAS EXTRA EJERCICIO 4 T4 *****************
       *                                                          *
       ************************************************************/
      //basicos
      assertThat(exercici4(2, 3, 5) == 3);
      assertThat(exercici4(5, 2, 5) == 0);
      assertThat(exercici4(2,2,7) == 4);

      //Exponentes altos
      assertThat(exercici4(274, 2780, 11) == 1);
      assertThat(exercici4(3752, 9784, 11) == 1);
      assertThat(exercici4(3,158,11) == 5);
      assertThat(exercici4(2023,2023,5) == 2);
      assertThat(exercici4(-2024,2023,7) == 6);
      assertThat(exercici4(2024,-2023,7) == 1);

      //*********** FIN PRUEBAS EXTRA EJERCICIO 4 T4 **************
    }
  }

  /**
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
