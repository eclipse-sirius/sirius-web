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
import { useLazyQuery } from '@apollo/client';
import gql from 'graphql-tag';
import { NewDocumentArea } from 'onboarding/NewDocumentArea';
import { NewRepresentationArea } from 'onboarding/NewRepresentationArea';
import { RepresentationsArea } from 'onboarding/RepresentationsArea';
import React, { useEffect, useState } from 'react';
import styles from './OnboardArea.module.css';
import { OnboardAreaProps } from './OnboardArea.types';

const getOnboardDataQuery = gql`
  query getOnboardData($editingContextId: ID!, $classId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        stereotypeDescriptions {
          id
          label
        }
        representationDescriptions(classId: $classId) {
          edges {
            node {
              id
              label
            }
          }
        }
        representations {
          id
          label
          kind
        }
      }
    }
  }
`;

const INITIAL_STATE = {
  stereotypeDescriptions: [],
  representationDescriptions: [],
  representations: [],
};

export const OnboardArea = ({ editingContextId, selection, setSelection, readOnly }: OnboardAreaProps) => {
  const [state, setState] = useState(INITIAL_STATE);
  const { stereotypeDescriptions, representationDescriptions, representations } = state;

  const classId = selection ? selection.kind : '';

  const [getOnboardData, { loading, data, error }] = useLazyQuery(getOnboardDataQuery);
  useEffect(() => {
    getOnboardData({ variables: { editingContextId, classId } });
  }, [editingContextId, classId, getOnboardData]);
  useEffect(() => {
    if (!loading && !error && data?.viewer) {
      const { viewer } = data;
      let representationDescriptions = viewer.editingContext.representationDescriptions.edges.map((edge) => edge.node);
      setState({
        representations: viewer.editingContext.representations,
        stereotypeDescriptions: viewer.editingContext.stereotypeDescriptions,
        representationDescriptions,
      });
    }
  }, [editingContextId, classId, loading, data, error]);

  return (
    <div className={styles.onboardArea}>
      <div className={styles.onboardContent}>
        <NewDocumentArea
          editingContextId={editingContextId}
          stereotypeDescriptions={stereotypeDescriptions}
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
