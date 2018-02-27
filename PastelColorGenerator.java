import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.awt.Color;

public class PastelColorGenerator{
	private List<Color> baseColors = Arrays.asList(Color.white);
	private Random random = new Random(System.currentTimeMillis());

	public PastelColorGenerator(){
	
	}

	public PastelColorGenerator(List<Color> colors){
		this.baseColors = colors;
	}

	public Color nextColor(){
		int index = random.nextInt(baseColors.size());
		Color baseColor = baseColors.get(index);
		float[] components = baseColor.getComponents(null);
		for(int i=0; i<components.length; ++i){
			components[i] = (components[i] + random.nextFloat())/2;
		}
		return new Color(baseColor.getColorSpace(), components, 1);

	}
}
