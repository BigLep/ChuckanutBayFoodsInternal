package com.chuckanutbay.documentation;

/**
 * Books, websites, etc. that explain technical concepts.
 */
public enum ReferenceSource {

	/**
	 * Staple resource for concurrency programming in Java.
	 * @see <a href="http://www.amazon.com/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601/ref=sr_1_1?ie=UTF8&s=books&qid=1260827759&sr=1-1">Amazon link</a>
	 */
	JCIP;

	/**
	 * @see <a href="http://www.amazon.com/Effective-Java-2nd-Joshua-Bloch/dp/0321356683/ref=sr_1_1?ie=UTF8&s=books&qid=1260827626&sr=8-1">Amazon link</a>
	 */
	public enum EffectiveJava {

		/**
		 * Item #1
		 */
		Static_factories_instead_of_constructors,

		/**
		 * Item #3
		 */
		Enforce_singleton,

		/**
		 * Item #4
		 */
		Enforce_noninstantiability,

		/**
		 * Item #6
		 */
		Eliminate_obsolete_object_references;
	}
}
