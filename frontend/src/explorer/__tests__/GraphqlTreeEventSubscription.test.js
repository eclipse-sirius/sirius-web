/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { loader } from 'graphql.macro';
import { getTreeEventSubscription } from '../getTreeEventSubscription';

describe('TreeEvent - subscription', () => {
  it('looks like the graphql subscription loaded from graphql test subscription file', () => {
    // Load graphql file
    const getDocumentSubscription = loader('./expectedTreeEventSubscription.test.graphql').loc.source.body.trim();
    // apply getTreeEventSubscription with depth 2
    const getBuiltSubscription = getTreeEventSubscription(2);
    // compare results
    const received = getBuiltSubscription.trim().replace(/\s+/g, ' ');
    const expected = getDocumentSubscription.replace(/\s+/g, ' ');
    expect(received).toBe(expected);
  });
});
