package PLAYER.GA;


public class HeapSort
{

    public static Individuals[] sort(Individuals individuals[])
    {
        int numberIndividuals = individuals.length;

        for (int sample = numberIndividuals/2; sample > 0; sample--)
            downheap(individuals, sample, numberIndividuals);

        do
        {
            Individuals currentIndividual = individuals[0];
            individuals[0] = individuals[numberIndividuals - 1];
            individuals[numberIndividuals - 1] = currentIndividual;

            numberIndividuals = numberIndividuals - 1;
            downheap(individuals, 1, numberIndividuals);
        }
        while (numberIndividuals > 1);
        return individuals;
    }

    private static void downheap(Individuals individuals[], int sample, int numberIndividuals)
    {
        Individuals currentIndividual = individuals[sample - 1];

        while (sample <= numberIndividuals/2)
        {
            int counter = sample + sample;
            if ((counter < numberIndividuals) && (individuals[counter - 1].getFitness() > individuals[counter].getFitness()))
                counter++;

            if (currentIndividual.getFitness() <= individuals[counter - 1].getFitness())
                break;

            else
            {
                individuals[sample - 1] = individuals[counter - 1];
                sample = counter;
            }
        }
        individuals[sample - 1] = currentIndividual;
    }
}

