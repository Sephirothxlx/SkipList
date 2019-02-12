package skiplist;

import java.util.Random;

public class SkipList {
	// The maximum level of the skip list is hard-coded.
	// Ideally, it would grow dynamically.
	final int MAXLEVEL = 9;

	private int level; // The highest level for any node currently in the skip
						// list
	private SkipNode head; // Header node with MAXLEVEL pointers
	private Random random;

	public SkipList() {
		this.random = new Random();
		this.head = new SkipNode();
		this.level = 0;
	}

	/*
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 * 
	 * @param key the key whose associated value is to be returned
	 * 
	 * @return the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key
	 */
	public String get(int key) {
		// Your code here
		String value = "";
		SkipNode x = this.head;
		for (int i = this.level-1; i >= 0; i--) {
			while (x.getForward(i)!=null&&x.getForward(i).getKey() < key) {
				x = x.getForward(i);
			}
		}
		x=x.getForward(0);
		if (x!=null&&x.getKey() == key) {
			value = x.getValue();
			return value;
		} else {
			return null;
		}
	}

	/*
	 * Associates the specified value with the specified key in this map. If the
	 * map previously contained a mapping for the key, the old value is
	 * replaced.
	 * 
	 * @param key key with which the specified value is to be associated
	 * 
	 * @param value value to be associated with the specified key
	 */
	public void put(int key, String value) {
		int newLevel = randomLevel();
		// Adjust head
		if (newLevel > MAXLEVEL) {
			newLevel = MAXLEVEL;
		}
		if (newLevel > this.level) {
			this.level = newLevel;
		}
		// Your code here
		SkipNode[] update = new SkipNode[MAXLEVEL];
		SkipNode x = this.head;
		for (int i = this.level-1; i >= 0; i--) {
			while (x.getForward(i)!=null&&x.getForward(i).getKey() < key) {
				x = x.getForward(i);
			}
			update[i] = x;
		}
		//get the forward node to check if the key is equal to target key
		x=x.getForward(0);
		//if the key is equal to some existing node
		//then we only need to update the value
		if (x!=null&&x.getKey() == key) {
			x.setValue(value);
		} else {
			SkipNode newNode = new SkipNode(key, value, newLevel);
			for (int i = newLevel-1; i >= 0; i--) {
				x = update[i];
				newNode.setForward(i, x.getForward(i));
				x.setForward(i, newNode);
			}
		}
	}

	/*
	 * Removes the mapping for the specified key from this map if present.
	 * 
	 * @param key key whose mapping is to be removed from the map
	 * 
	 * @return the previous value associated with key, or null if there was no
	 * mapping for key.
	 */
	public String remove(int key) {
		// Your code here
		SkipNode[] update = new SkipNode[MAXLEVEL];
		SkipNode x = this.head;
		for (int i = this.level-1; i >= 0; --i) {
			while (x.getForward(i)!=null&&x.getForward(i).getKey() < key) {
				x = x.getForward(i);
			}
			update[i] = x;
		}

		String value = "";
		x = x.getForward(0);
		//if there is not target key
		if (x!=null&&x.getKey() != key) {
			return null;
		} else {
			value = x.getValue();
			for (int i = 0; i < this.level; i++) {
				//it means that the upper level cannot have this key
				// so we don't need to remove
				if (update[i].getForward(i) != x)
					break;
				//let GC mange memory
				update[i].setForward(i, x.getForward(i));
			}
			//if head.getForward(i)==null, the ith level is empty
			//level-1
			while (this.level > 0 && this.head.getForward(this.level) == null) {
				this.level--;
			}

			return value;
		}
	}

	/*
	 * Returns a random level
	 * 
	 * @return the level
	 */
	private int randomLevel() {
		// Your code here
		int k=1;
		while((int)(Math.random()*2)==1)
			k++;
		return k;
	}

	@Override
	public String toString() {
		String output = "";

		for (int i = 0; i < this.level; i++) {
			output += "*** Level " + i + " ***\n";

			SkipNode current = this.head;
			while (current.getForward(i) != null) {
				current = current.getForward(i);
				output += current.getKey() + "\t" + current.getValue() + "\t" + current.getLevel() + "\n";
			}
		}

		return output;
	}

	private class SkipNode {
		private int key;
		private String value;
		private int level;
		private SkipNode[] forward;

		private SkipNode() {
			this(0, null, MAXLEVEL);
		}

		private SkipNode(int key, String value) {
			this(key, value, MAXLEVEL);
		}

		private SkipNode(int key, String value, int level) {
			this.level = level;
			this.key = key;
			this.value = value;
			this.forward = new SkipNode[this.level];
			for (int i = 0; i < this.level; i++)
				this.forward[i] = null;
		}

		private int getKey() {
			return this.key;
		}

		private String getValue() {
			return this.value;
		}

		private void setValue(String value) {
			this.value = value;
		}

		private int getLevel() {
			return this.level;
		}

		private SkipNode getForward(int i) {
			return this.forward[i];
		}

		private void setForward(int i, SkipNode node) {
			this.forward[i] = node;
		}
	}
}
