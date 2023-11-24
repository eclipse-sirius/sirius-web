/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { MainAreaComponentProps } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { NewDocumentArea } from './NewDocumentArea';
import { NewRepresentationArea } from './NewRepresentationArea';
import { OnboardAreaState } from './OnboardArea.types';
import { RepresentationsArea } from './RepresentationsArea';

const getOnboardDataQuery = gql`
  query getOnboardData($editingContextId: ID!, $objectId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representationDescriptions(objectId: $objectId) {
          edges {
            node {
              id
              label
              defaultName
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

const INITIAL_STATE: OnboardAreaState = {
  editingContextActions: [],
  representationDescriptions: [],
  representations: [],
};

const useOnboardAreaStyles = makeStyles((theme) => ({
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'stretch',
    padding: theme.spacing(5),
    overflowY: 'auto',
    overflowX: 'auto',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))',
    gap: theme.spacing(2),
  },
  box: {},
}));

export const OnboardArea = ({ editingContextId, selection, setSelection, readOnly }: MainAreaComponentProps) => {
  const classes = useOnboardAreaStyles();
  const [state, setState] = useState<OnboardAreaState>(INITIAL_STATE);
  const { editingContextActions, representationDescriptions, representations } = state;

  const objectId = selection.entries.length > 0 ? selection.entries[0].id : '';

  const [getOnboardData, { loading, data, error }] = useLazyQuery(getOnboardDataQuery);
  useEffect(() => {
    getOnboardData({ variables: { editingContextId, objectId } });
  }, [editingContextId, objectId, getOnboardData]);
  useEffect(() => {
    if (!loading && !error && data?.viewer) {
      const { viewer } = data;
      const representations = viewer.editingContext.representations.edges.map((edge) => edge.node);
      const editingContextActions = viewer.editingContext.actions.edges.map((edge) => edge.node);
      const representationDescriptions = viewer.editingContext.representationDescriptions.edges.map(
        (edge) => edge.node
      );

      setState({
        representations,
        editingContextActions,
        representationDescriptions,
      });
    }
  }, [editingContextId, objectId, loading, data, error]);

  return (
    <div className={classes.container}>
      <div className={classes.grid}>
        <NewDocumentArea
          editingContextId={editingContextId}
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
