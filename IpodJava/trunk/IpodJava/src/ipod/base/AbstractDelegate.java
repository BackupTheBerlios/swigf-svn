/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.base;

/**
 * Baseclass for a hierarchy of classes connected to another hierarchy of classes through delegation.
 * Subclasses have to provide an additional constructor, which does not create an instance of the
 * delegate.
 */
public abstract class AbstractDelegate {
	protected Object delegate;
	
}
