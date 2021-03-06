/*******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.common.componentcore;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.wst.common.componentcore.internal.ComponentResource;
import org.eclipse.wst.common.componentcore.internal.ModulecorePlugin;
import org.eclipse.wst.common.componentcore.internal.StructureEdit;
import org.eclipse.wst.common.componentcore.internal.impl.ResourceTreeNode;
import org.eclipse.wst.common.componentcore.internal.resources.VirtualFile;
import org.eclipse.wst.common.componentcore.internal.resources.VirtualReference;
import org.eclipse.wst.common.componentcore.internal.resources.VirtualResource;
import org.eclipse.wst.common.componentcore.internal.util.ComponentImplManager;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.componentcore.resources.IVirtualReference;
import org.eclipse.wst.common.componentcore.resources.IVirtualResource;

/**
 * Provides a handle creation factory for the Virtual Path API. Clients may use
 * this class to convert Platform IResource model elements to IVirtualResource
 * model elements.
 * <p>
 * ComponentCore provides a consistent entry point to the IVirtual Path model
 * that allows clients to group resources together in logical collections with a
 * path structure that varies from their actual source location structures.
 * </p>
 * 
 * @plannedfor 1.0
 */
public class ComponentCore {

	private static final IVirtualResource[] NO_RESOURCES = new VirtualResource[0];

	/**
	 * Return an IVirtualComponent with the given name (aComponentName)
	 * contained by the given project (aProject). Component names should be
	 * unique across a project.
	 * 
	 * @param aProject
	 *            A valid, accessible project to contain the component
	 * @return A handle to an IVirtualComponent that may or may not exist or
	 *         null if passed project does not contain ModuleCoreNature.
	 * @see org.eclipse.core.runtime.IProgressMonitor#create(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public static IVirtualComponent createComponent(IProject aProject) {
		if (aProject == null || !aProject.isAccessible()){
			return null;
		}
		return ComponentImplManager.instance().createComponent(aProject);
	}

	/**
	 * Return an IVirtualComponent with the given name (aComponentName)
	 * contained by the given project (aProject). Component names should be
	 * unique across a project.
	 * 
	 * @param aProject
	 *            A valid, accessible project to contain the component
	 * @param checkForComponentFile
	 *            A flag to indicate if the presence of the component file should be checked
	 * @return A handle to an IVirtualComponent that may or may not exist or
	 *         null if passed project does not contain ModuleCoreNature.
	 * @see org.eclipse.core.runtime.IProgressMonitor#create(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public static IVirtualComponent createComponent(IProject aProject, boolean checkForComponentFile) {
		if (aProject == null || !aProject.isAccessible()){
			return null;
		}
		return ComponentImplManager.instance().createComponent(aProject, checkForComponentFile);
	}

	/**
	 * Return an IVirtualComponent with the given name (aComponentName)
	 * contained by the given project (aProject). Component names should be
	 * unique across a project.
	 * 
	 * @param aProject
	 *            A valid, accessible project to contain the component
	 * @return A handle to an IVirtualComponent that may or may not exist or
	 *         null if passed project does not contain ModuleCoreNature.
	 * @deprecated
	 * @see org.eclipse.core.runtime.IProgressMonitor#create(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public static IVirtualComponent createComponent(IProject aProject, String aName) {
		return createComponent(aProject);
	}

	/**
	 * Return an IVirtualComponent with the given name (aComponentName)
	 * 
	 * @param aComponentName
	 *            A name to identify the component, the name can be
	 *            lib/&lt;Absolute path of a jar&gt; or
	 *            var/&lt;CLASSPATH_VARIABLE/library namer&gt;
	 * @return A handle to an IVirtualComponent that may or may not exist.
	 * @see org.eclipse.core.runtime.IProgressMonitor#create(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public static IVirtualComponent createArchiveComponent(IProject aProject, String aComponentName) {
		return ComponentImplManager.instance().createArchiveComponent(aProject, aComponentName);
	}

	/**
	 * Return an IVirtualComponent with the given name (aComponentName)
	 * and the given runtime path (path)
	 * 
	 * @param aComponentName
	 *            A name to identify the component, the name can be
	 *            lib/&lt;Absolute path of a jar&gt; or
	 *            var/&lt;CLASSPATH_VARIABLE/library namer&gt;
	 * @return A handle to an IVirtualComponent that may or may not exist.
	 * @see org.eclipse.core.runtime.IProgressMonitor#create(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public static IVirtualComponent createArchiveComponent(IProject aProject, String aComponentName, IPath runtimePath) {
		return ComponentImplManager.instance().createArchiveComponent(aProject, aComponentName, runtimePath);
	}

	/**
	 * Return an IVirtualFolder with a runtime path specified by aRuntimePath
	 * contained by aProject, in a component named aComponentName. The resultant
	 * IVirtualFolder may or may not exist.
	 * 
	 * @param aProject
	 *            A valid, accessible project to contain the component
	 * @param aRuntimePath
	 *            The runtime path of the IVirtualFolder to return.
	 * @return An IVirtualFolder contained by the specified component with the
	 *         given runtime path
	 * @see org.eclipse.core.runtime.IProgressMonitor#create(int, org.eclipse.core.runtime.IProgressMonitor)
	 * @see IVirtualResource#createLink(IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public static IVirtualFolder createFolder(IProject aProject, IPath aRuntimePath) {
		return ComponentImplManager.instance().createFolder(aProject, aRuntimePath);
	}

	/**
	 * Return an IVirtualFile with a runtime path specified by aRuntimePath
	 * contained by aProject, in a component named aComponentName. IVirtualFiles
	 * can only be created as links. Use
	 * {@link IVirtualResource#getUnderlyingResource()} or
	 * {@link IVirtualFile#getUnderlyingFile()} to create a resource with real
	 * contents.
	 * 
	 * @param aProject
	 *            A valid, accessible project to contain the component
	 * @param aRuntimePath
	 *            The runtime path of the IVirtualFolder to return.
	 * @return An IVirtualFile contained by the specified component with the
	 *         given runtime path
	 * @see IVirtualResource#createLink(IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public static IVirtualFile createFile(IProject aProject, IPath aRuntimePath) {
		return new VirtualFile(aProject, aRuntimePath);
	}

	/**
	 * Return an IVirtualReference that captures a relationship between
	 * aComponent and aReferencedComponent. The IVirtualReference will be stored
	 * with aComponent and target aReferencedComponent. IVirtualReferences may
	 * span projects.
	 * 
	 * @param aComponent
	 *            A valid, existing IVirtualComponent
	 * @param aReferencedComponent
	 *            A valid, existing IVirtualComponent
	 * @return An IVirtualReference that captures the relationship between
	 *         aComponent and aReferencedComponent.
	 * @see IVirtualReference#create(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public static IVirtualReference createReference(IVirtualComponent aComponent, IVirtualComponent aReferencedComponent) {
		return new VirtualReference(aComponent, aReferencedComponent);
	}

	public static IVirtualReference createReference(IVirtualComponent aComponent, IVirtualComponent aReferencedComponent, IPath runtimePath) {
		return new VirtualReference(aComponent, aReferencedComponent, runtimePath);
	}

	/**
	 * Return an array of IVirtualResources that represent the given IResource.
	 * Each IResource could be mapped to multiple components, and thus an array
	 * of each IVirtualResource that represents the IResource will be returned.
	 * Each IVirtualResource may also map to other existing IResources, so the
	 * mapping is not 1:1.
	 * 
	 * @param aResource
	 *            An accessible IResource
	 * @return An array of IVirtualResources from the model that represent the
	 *         IResource.
	 */
	public static IVirtualResource[] createResources(IResource aResource) {
		IProject proj = aResource.getProject();
		StructureEdit se = null;
		List foundResources = new ArrayList();
		try {
			se = StructureEdit.getStructureEditForRead(proj);
			if (se != null) {
				ComponentResource[] resources = se.findResourcesBySourcePath(aResource.getProjectRelativePath(), aResource.exists() ? ResourceTreeNode.CREATE_NONE : ResourceTreeNode.CREATE_RESOURCE_ALWAYS);
				for (int i = 0; i < resources.length; i++) {
					if (aResource.getType() == IResource.FILE)
						foundResources.add(new VirtualFile(proj, resources[i].getRuntimePath()));
					else
						foundResources.add(ComponentCore.createFolder(proj, resources[i].getRuntimePath()));
				}
			}
		} catch (UnresolveableURIException e) {
			ModulecorePlugin.logError(e);
		} finally {
			if (se != null) {
				se.dispose();
			}
		}
		if (foundResources.size() > 0)
			return (IVirtualResource[]) foundResources.toArray(new VirtualResource[foundResources.size()]);
		return NO_RESOURCES;
	}
}
