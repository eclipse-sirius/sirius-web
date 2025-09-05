/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { gql } from '@apollo/client';
import { expect, test } from 'vitest';
import { getTreeEventSubscription } from '../getTreeEventSubscription';

const getDocumentSubscription = gql`
  subscription explorerEvent($input: ExplorerEventInput!) {
    explorerEvent(input: $input) {
      __typename
      ... on TreeRefreshedEventPayload {
        id
        tree {
          id
          children {
            ...treeItemFields
            children {
              ...treeItemFields
              children {
                ...treeItemFields
              }
            }
          }
          capabilities {
            useExplorerPalette
          }
        }
      }
    }
  }

  fragment treeItemFields on TreeItem {
    id
    hasChildren
    expanded
    label {
      styledStringFragments {
        text
        styledStringFragmentStyle {
          isStruckOut
          underlineStyle
          borderStyle
          font
          backgroundColor
          foregroundColor
          strikeoutColor
          underlineColor
          borderColor
          isBold
          isItalic
        }
      }
    }
    editable
    deletable
    selectable
    kind
    iconURL
  }
`.loc.source.body.trim();

test('looks like the graphql subscription loaded from graphql test subscription file', () => {
  // apply getTreeEventSubscription with depth 2
  const getBuiltSubscription = getTreeEventSubscription(2, 'explorerEvent', 'ExplorerEventInput');
  // compare results
  const received = getBuiltSubscription.trim().replace(/\s+/g, ' ');
  const expected = getDocumentSubscription.replace(/\s+/g, ' ');
  expect(received).toBe(expected);
});
