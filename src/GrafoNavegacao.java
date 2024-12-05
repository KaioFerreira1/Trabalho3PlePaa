import java.util.*;

public class GrafoNavegacao {

    static class Grafo {
        private final Map<String, List<String>> adjacencias = new HashMap<>();

        public void adicionarEstrada(String cidade1, String cidade2) {
            adjacencias.computeIfAbsent(cidade1, k -> new ArrayList<>()).add(cidade2);
            adjacencias.computeIfAbsent(cidade2, k -> new ArrayList<>()).add(cidade1);
        }

        public List<String> getAdjacentes(String cidade) {
            return adjacencias.getOrDefault(cidade, Collections.emptyList());
        }
    }

    public static List<String> buscaEmLargura(Grafo grafo, String inicio) {
        List<String> ordemVisita = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        Queue<String> fila = new LinkedList<>();

        fila.add(inicio);
        visitados.add(inicio);

        while (!fila.isEmpty()) {
            String cidadeAtual = fila.poll();
            ordemVisita.add(cidadeAtual);

            for (String vizinho : grafo.getAdjacentes(cidadeAtual)) {
                if (!visitados.contains(vizinho)) {
                    fila.add(vizinho);
                    visitados.add(vizinho);
                }
            }
        }

        return ordemVisita;
    }

    public static List<String> buscaEmProfundidade(Grafo grafo, String inicio) {
        List<String> ordemVisita = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        Stack<String> pilha = new Stack<>();

        pilha.push(inicio);

        while (!pilha.isEmpty()) {
            String cidadeAtual = pilha.pop();

            if (!visitados.contains(cidadeAtual)) {
                visitados.add(cidadeAtual);
                ordemVisita.add(cidadeAtual);

                for (String vizinho : grafo.getAdjacentes(cidadeAtual)) {
                    if (!visitados.contains(vizinho)) {
                        pilha.push(vizinho);
                    }
                }
            }
        }

        return ordemVisita;
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        //criando o grafo com cidades e estradas
        grafo.adicionarEstrada("São Paulo", "Rio de Janeiro");
        grafo.adicionarEstrada("São Paulo", "Belo Horizonte");
        grafo.adicionarEstrada("Rio de Janeiro", "Salvador");
        grafo.adicionarEstrada("Belo Horizonte", "Brasília");
        grafo.adicionarEstrada("Belo Horizonte", "Salvador");
        grafo.adicionarEstrada("Salvador", "Fortaleza");

        //Cidade inicial
        String cidadeInicial = "São Paulo";

        //Executo as buscas
        List<String> resultadoBFS = buscaEmLargura(grafo, cidadeInicial);
        List<String> resultadoDFS = buscaEmProfundidade(grafo, cidadeInicial);

        //resultados
        System.out.println("Busca em Largura (BFS) a partir de " + cidadeInicial + ": " + resultadoBFS);
        System.out.println("Busca em Profundidade (DFS) a partir de " + cidadeInicial + ": " + resultadoDFS);
    }
}