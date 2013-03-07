package year2013;

public class MapEntry<K extends Comparable<K>, V>
	implements Comparable<MapEntry<K, V>>
{
	public K key;
	public V value;
	
	public MapEntry(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	public int compareTo(MapEntry<K, V> entry)
	{
		return key.compareTo(entry.key);
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj)
	{
		if (obj instanceof MapEntry)
			return compareTo((MapEntry<K, V>)obj) == 0;
		return false;
	}
}