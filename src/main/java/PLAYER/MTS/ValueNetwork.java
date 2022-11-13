package main.java.PLAYER.MTS;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class ValueNetwork {
    private int FEATURES_COUNT=37; //number of cells on board
    private int CLASSES_COUNT;

  // EPOCH : number of time we're working on the whole dataset
    // iteration : number of time we're working on an individual node

    MultiLayerConfiguration configuration
            = new NeuralNetConfiguration.Builder()
            .iterations(1000)//number of time one identical node is used
            .activation(Activation.TANH)
            .weightInit(WeightInit.XAVIER) //normally distributed initial weights
            .learningRate(0.1) //speed at which the weights are updated
            .regularization(true).l2(0.0001) //used to avoid overfitting
            .list()
            .layer(0, new DenseLayer.Builder().nIn(FEATURES_COUNT).nOut(3).build())
            .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).build())
            .layer(2, new OutputLayer.Builder(
                    LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                    .activation(Activation.SOFTMAX)
                    .nIn(3).nOut(CLASSES_COUNT).build())
            .backprop(true).pretrain(false)
            .build();

}
