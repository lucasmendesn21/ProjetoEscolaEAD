public class ListaDeAlunos
{
    private Aluno[] alunos;
    private int totalAlunos;

    public ListaDeAlunos(int capacidade)
    {
        alunos = new Aluno[capacidade];
        totalAlunos = 0;
    }

    public boolean adicionarAluno(Aluno a)
    {
        if (a == null)
        {
            return false;
        }

        // Verifica código duplicado
        for (int i = 0; i < totalAlunos; i++)
        {
            if (alunos[i].getCodigo() == a.getCodigo())
            {
                return false;
            }
        }

        // Verifica se o vetor está cheio
        if (totalAlunos >= alunos.length)
        {
            return false;
        }

        alunos[totalAlunos] = a;
        totalAlunos++;

        return true;
    }

    public void exibirLista()
    {
        if (totalAlunos == 0)
        {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        for (int i = 0; i < totalAlunos; i++)
        {
            System.out.println("-----------------------------");
            alunos[i].exibeDados();
        }

        System.out.println("-----------------------------");
    }

    public Aluno buscarAluno(int codigo)
    {
        for (int i = 0; i < totalAlunos; i++)
        {
            if (alunos[i].getCodigo() == codigo)
            {
                return alunos[i];
            }
        }

        return null;
    }

    public Aluno buscarAlunoPorIndice(int indice)
    {
        if (indice < 0 || indice >= totalAlunos)
        {
            return null;
        }

        return alunos[indice];
    }

    public int getTotalAlunos()
    {
        return totalAlunos;
    }
}