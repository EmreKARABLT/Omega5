package GAME;

import org.tensorflow.*;
import org.tensorflow.proto.framework.SavedModel;

import java.nio.IntBuffer;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class HelloTensorFlow {
	private SavedModelBundle model;
	private Session session ;
	private Session.Runner runner ;
	public HelloTensorFlow(String model_path){
		try {
			model = SavedModelBundle.load(model_path, "serve");
			session = model.session();
		}
		catch (Exception ignored){

		}
	}
	public double predict(ArrayList<Cell> board ){
			float[][] dst = new float[1][1];
			if(model==null) return -1;
			// Create a session to run the model
			try {

				// Input tensor
				float[][] inputValues = new float[1][37];
				for (int i = 0; i < inputValues[0].length; i++) {
					inputValues[0][i] = board.get(i).getColor()+1;
				}
				Tensor input = Tensor.create(inputValues);
				Tensor output = session.runner().feed("serving_default_input_1:0", input).fetch("StatefulPartitionedCall:0").run().get(0);
				// Get the predictions
				output.copyTo(dst);
//				System.out.println("Prediction: " + dst[0][0]);
			}catch (Exception ignored){

			}
		return dst[0][0];
	}

	public double predict(int[] board ){
			float[][] dst = new float[1][1];
			if(model==null) return -1;
			// Create a session to run the model
			try {

				// Input tensor
				float[][] inputValues = new float[1][37];
				for (int i = 0; i < inputValues[0].length; i++) {
					inputValues[0][i] = board[i];
				}
				Tensor input = Tensor.create(inputValues);
				Tensor output = runner.feed("serving_default_input_1:0", input).fetch("StatefulPartitionedCall:0").run().get(0);
				// Get the predictions
				output.copyTo(dst);
				System.out.println("Prediction: " + dst[0][0]);
			}catch (Exception ignored){

			}
		return dst[0][0];
	}

	public static void main(String[] args) {
		HelloTensorFlow model = new HelloTensorFlow("C:/Users/bpk_e/Desktop/NN1/try_model");
		double start = System.currentTimeMillis();

		int N = 100;
		for (int i = 0; i < N; i++) {
			int[] board = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0};
			model.predict(board);
		}
		double finish = System.currentTimeMillis();
		System.out.println( (finish-start));
	}
}