/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

/**
 * Computes the class names of an element from the given static class names
 * along with optional class names which can be null or undefined.
 */
export const classNames = (staticClassNames, ...optionalClassNames) => {
  const classNames = staticClassNames + ' ' + optionalClassNames.filter(n => n).join(' ');
  return classNames.trim();
};
