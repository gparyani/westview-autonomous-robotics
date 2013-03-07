package year2013;

import java.util.ArrayList;
import java.util.Collections;

public class Mapping<K extends Comparable<K>, V>
{
	private SortedList<MapEntry<K, V>> entries;
	
	public Mapping()
	{
		entries = new SortedList<MapEntry<K, V>>();
	}
	
	private int indexOf(K key)
	{
		return entries.indexOf(new MapEntry<K, V>(key, null));
	}
	
	public V get(K key)
	{
		return entries.get(indexOf(key)).value;
	}
	
	public void add(K key, V value)
	{
		entries.add(new MapEntry<K, V>(key, value));
	}
	public boolean remove(K key)
	{
		return entries.remove(indexOf(key)) != null;
	}
	
	public MapEntry<K, V> lower(K key)
	{
		for (int i = entries.size() - 1; i >= 0; i--)
		{
			MapEntry<K, V> entry = entries.get(i);
			if (entry.key.compareTo(key) < 0)
				return entry;
		}
		return null;
	}
	public MapEntry<K, V> higher(K key)
	{
		for (int i = 0; i < entries.size(); i++)
		{
			MapEntry<K, V> entry = entries.get(i);
			if (entry.key.compareTo(key) > 0)
				return entry;
		}
		return null;
	}

	public int size()
	{
		return entries.size();
	}
	
	class SortedList<T extends Comparable<T>> extends ArrayList<T>
	{
		private static final long serialVersionUID = 1L;

		public boolean add(T value)
		{
	        boolean result = super.add(value);
	        Comparable<T> cmp = (Comparable<T>) value;
	        for (int i = size() - 1; i > 0 && cmp.compareTo(get(i - 1)) < 0; i--)
	            Collections.swap(this, i, i - 1);
	        return result;
	    }
	}
}