/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { gql, useLazyQuery } from '@apollo/client';
import { NewDocumentArea } from 'onboarding/NewDocumentArea';
import { NewRepresentationArea } from 'onboarding/NewRepresentationArea';
import { RepresentationsArea } from 'onboarding/RepresentationsArea';
import React, { useEffect, useState } from 'react';
import { MainAreaComponentProps } from 'workbench/Workbench.types';
import styles from './OnboardArea.module.css';

const getOnboardDataQuery = gql`
  query getOnboardData($editingContextId: ID!, $kind: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        stereotypeDescriptions {
          edges {
            node {
              id
              label
              documentName
            }
          }
        }
        representationDescriptions(kind: $kind) {
          edges {
            node {
              id
              label
            }
          }
        }
        representations {
          edges {
            node {
              id
              label
              kind
            }
          }
        }
        actions {
          edges {
            node {
              id
              label
            }
          }
        }
      }
    }
  }
`;

const INITIAL_STATE = {
  stereotypeDescriptions: [],
  editingContextActions: [],
  representationDescriptions: [],
  representations: [],
};

export const OnboardArea = ({ editingContextId, selection, setSelection, readOnly }: MainAreaComponentProps) => {
  const [state, setState] = useState(INITIAL_STATE);
  const { stereotypeDescriptions, editingContextActions, representationDescriptions, representations } = state;

  const kind = selection.entries.length > 0 ? selection.entries[0].kind : '';

  const [getOnboardData, { loading, data, error }] = useLazyQuery(getOnboardDataQuery);
  useEffect(() => {
    getOnboardData({ variables: { editingContextId, kind } });
  }, [editingContextId, kind, getOnboardData]);
  useEffect(() => {
    if (!loading && !error && data?.viewer) {
      const { viewer } = data;
      const representations = viewer.editingContext.representations.edges.map((edge) => edge.node);
      const stereotypeDescriptions = viewer.editingContext.stereotypeDescriptions.edges.map((edge) => edge.node);
      const editingContextActions = viewer.editingContext.actions.edges.map((edge) => edge.node);
      const representationDescriptions = viewer.editingContext.representationDescriptions.edges.map(
        (edge) => edge.node
      );

      setState({
        representations,
        stereotypeDescriptions,
        editingContextActions,
        representationDescriptions,
      });
    }
  }, [editingContextId, kind, loading, data, error]);

  return (
    <div className={styles.onboardArea}>
      <div className={styles.onboardContent}>
        <NewDocumentArea
          editingContextId={editingContextId}
          stereotypeDescriptions={stereotypeDescriptions}
          editingContextActions={editingContextActions}
          setSelection={setSelection}
          readOnly={readOnly}
        />
        <NewRepresentationArea
          editingContextId={editingContextId}
          representationDescriptions={representationDescriptions}
          selection={selection}
          setSelection={setSelection}
          readOnly={readOnly}
        />
        <RepresentationsArea representations={representations} setSelection={setSelection} />
      </div>
    </div>
  );
};
