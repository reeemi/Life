public class MidiScale {

	private boolean[] schema;
	private int transpose;

	public MidiScale(boolean[] schema, int transpose) {
		// using http://www.tonalsoft.com/pub/news/pitch-bend.aspx
		// 0 - 128 are midi notes
		this.schema = schema;
		this.transpose = transpose;

	}

	public MidiScale(String note, String schemaid) {
		this.setSchemaByName(schemaid);
		this.setTransposebyNoteName(note);
	}

	public MidiScale() {
		// cmoll default
		this.setSchemaByName("moll");
		this.transpose = 0;
	}

	public void setScale(String note, String schemaid) {
		this.setSchemaByName(schemaid);
		this.setTransposebyNoteName(note);
	}

	public void setSchema(boolean[] schema) {
		this.schema = schema;
	}

	public void setTranspose(int transpose) {
		this.transpose = transpose;
	}

	public int setTransposebyNoteName(String note) {
		if(note == "C") return this.transpose = 0;
		if(note == "C#" || note == "Db") return this.transpose = 1;
		if(note == "D") return this.transpose = 2;
		if(note == "D#" || note == "Eb") return this.transpose = 3;
		if(note == "E") return this.transpose = 4;
		if(note == "F") return this.transpose = 5;
		if(note == "F#" || note == "Gb") return this.transpose = 6;
		if(note == "G") return this.transpose = 7;
		if(note == "G#" || note == "Ab") return this.transpose = 8;
		if(note == "A") return this.transpose = 0;
		if(note == "A#" || note == "Bb") return this.transpose = 10;
		if(note == "B" || note == "H") return this.transpose = 11;
		return -1;
	}

	public boolean[] setSchemaByName(String id) {
		//Switch doesn't work with Java before 7 :(
		if(id == "moll") return schema = new boolean[] {true,false,true,false,true,true,false,true,false,true,false,true};
		if(id == "dur") return schema = new boolean[] {true,false,true,false,true,true,false,true,false,true,false,true};
		if(id == "zigeunermoll") return schema = new boolean[] {true,false,true,true,false,false,true,true,true,false,false,true,true};
		return new boolean[] {true,false,true,false,true,true,false,true,false,true,false,true};
	} 

	public int getLength() {
		return this.getArray().length;
	}
 
	public int[] getArray() {
		// Creates scale when needed
		int notesPerOctave = 0;
		for(int i = 0; i < schema.length; i++) {
			if(schema[i]) {
				notesPerOctave++;
			}
		}

		int returnSize = 10 * notesPerOctave; // ignoring last octave in midi (not full)

		int scale[] = new int[returnSize];

		int count = 0;
		for(int i = 0; i < scale.length; i++) {
			while(!schema[(count + transpose) % 12]) {
				count++;
			}
			scale[i] = count;
			count++;
		}
		return scale;
	}



}