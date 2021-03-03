import gql from 'graphql-tag';
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
import { getTreeEventSubscription } from '../getTreeEventSubscription';

const getDocumentSubscription = gql`
  subscription treeEvent($input: TreeEventInput!) {
    treeEvent(input: $input) {
      __typename
      ... on TreeRefreshedEventPayload {
        id
        tree {
          id
          label
          children {
            ...treeItemFields
            children {
              ...treeItemFields
              children {
                ...treeItemFields
              }
            }
          }
        }
      }
    }
  }

  fragment treeItemFields on TreeItem {
    id
    hasChildren
    expanded
    label
    editable
    kind
    imageURL
  }
`.loc.source.body.trim();

describe('TreeEvent - subscription', () => {
  it('looks like the graphql subscription loaded from graphql test subscription file', () => {
    // apply getTreeEventSubscription with depth 2
    const getBuiltSubscription = getTreeEventSubscription(2);
    // compare results
    const received = getBuiltSubscription.trim().replace(/\s+/g, ' ');
    const expected = getDocumentSubscription.replace(/\s+/g, ' ');
    expect(received).toBe(expected);
  });
});
