/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { gql, useSubscription } from '@apollo/client';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { useComponent } from '../extension/useComponent';
import { useData } from '../extension/useData';
import { useSelection } from '../selection/useSelection';
import { Toast } from '../toast/Toast';
import { Panels } from './Panels';
import { RepresentationNavigation } from './RepresentationNavigation';
import {
  GQLEditingContextEventSubscription,
  RepresentationComponentProps,
  RepresentationMetadata,
  WorkbenchProps,
  WorkbenchViewContribution,
} from './Workbench.types';
import {
  representationFactoryExtensionPoint,
  workbenchMainAreaExtensionPoint,
  workbenchViewContributionExtensionPoint,
} from './WorkbenchExtensionPoints';
import {
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideRepresentationEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  UpdateSelectedRepresentationEvent,
  WorkbenchContext,
  WorkbenchEvent,
  workbenchMachine,
} from './WorkbenchMachine';

const editingContextEventSubscription = gql`
  subscription editingContextEvent($input: EditingContextEventInput!) {
    editingContextEvent(input: $input) {
      __typename
      ... on RepresentationRenamedEventPayload {
        representationId
        newLabel
      }
    }
  }
`;

const useWorkbenchStyles = makeStyles(() => ({
  main: {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
  },
  representationArea: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
    overflow: 'auto',
  },
}));

export const Workbench = ({
  editingContextId,
  initialRepresentationSelected,
  onRepresentationSelected,
  readOnly,
}: WorkbenchProps) => {
  const classes = useWorkbenchStyles();
  const [{ value, context }, dispatch] = useMachine<WorkbenchContext, WorkbenchEvent>(workbenchMachine, {
    context: {
      displayedRepresentation: initialRepresentationSelected,
      representations: initialRepresentationSelected ? [initialRepresentationSelected] : [],
    },
  });
  const { toast } = value as SchemaValue;
  const { id, representations, displayedRepresentation, message } = context;
  const { selection, setSelection } = useSelection();
  const { data: representationFactories } = useData(representationFactoryExtensionPoint);

  const { error } = useSubscription<GQLEditingContextEventSubscription>(editingContextEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
      },
    },
    fetchPolicy: 'no-cache',
    onData: ({ data }) => {
      const handleDataEvent: HandleSubscriptionResultEvent = {
        type: 'HANDLE_SUBSCRIPTION_RESULT',
        result: data,
      };
      dispatch(handleDataEvent);
    },
    onComplete: () => {
      const completeEvent: HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
      dispatch(completeEvent);
    },
  });

  useEffect(() => {
    if (error) {
      const { message } = error;
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  }, [error, dispatch]);

  useEffect(() => {
    const representations: RepresentationMetadata[] = selection.entries.filter((entry) =>
      entry.kind.startsWith('siriusComponents://representation')
    );
    const updateSelectedRepresentation: UpdateSelectedRepresentationEvent = {
      type: 'UPDATE_SELECTED_REPRESENTATION',
      representations,
    };
    dispatch(updateSelectedRepresentation);
  }, [selection, dispatch]);

  const onRepresentationClick = (representation: RepresentationMetadata) => {
    setSelection({ entries: [{ id: representation.id, label: representation.label, kind: representation.kind }] });
  };

  const onClose = (representation: RepresentationMetadata) => {
    const hideRepresentationEvent: HideRepresentationEvent = { type: 'HIDE_REPRESENTATION', representation };
    dispatch(hideRepresentationEvent);
  };

  useEffect(() => {
    if (displayedRepresentation && displayedRepresentation.id !== initialRepresentationSelected?.id) {
      onRepresentationSelected(displayedRepresentation);
    } else if (displayedRepresentation === null && initialRepresentationSelected) {
      onRepresentationSelected(null);
    }
  }, [onRepresentationSelected, initialRepresentationSelected, displayedRepresentation]);

  const workbenchViewLeftSideContributions: WorkbenchViewContribution[] = [];
  const workbenchViewRightSideContributions: WorkbenchViewContribution[] = [];

  const { data: workbenchViewContributions } = useData(workbenchViewContributionExtensionPoint);
  for (const workbenchViewContribution of workbenchViewContributions) {
    if (workbenchViewContribution.side === 'left') {
      workbenchViewLeftSideContributions.push(workbenchViewContribution);
    } else if (workbenchViewContribution.side === 'right') {
      workbenchViewRightSideContributions.push(workbenchViewContribution);
    }
  }

  const { Component: MainComponent } = useComponent(workbenchMainAreaExtensionPoint);
  let main = <MainComponent editingContextId={editingContextId} readOnly={readOnly} />;

  if (displayedRepresentation) {
    const RepresentationComponent = representationFactories
      .map((representationFactory) => representationFactory(displayedRepresentation))
      .find((component) => component != null);
    const props: RepresentationComponentProps = {
      editingContextId,
      readOnly,
      representationId: displayedRepresentation.id,
    };
    if (RepresentationComponent) {
      main = (
        <div className={classes.representationArea} data-testid="representation-area">
          <RepresentationNavigation
            representations={representations}
            displayedRepresentation={displayedRepresentation}
            onRepresentationClick={onRepresentationClick}
            onClose={onClose}
          />
          <RepresentationComponent key={`${editingContextId}#${displayedRepresentation.id}`} {...props} />
        </div>
      );
    }
  }

  return (
    <>
      <Panels
        editingContextId={editingContextId}
        readOnly={readOnly}
        leftContributions={workbenchViewLeftSideContributions}
        leftPanelInitialSize={400}
        rightContributions={workbenchViewRightSideContributions}
        rightPanelInitialSize={450}
        mainArea={main}
      />
      <Toast
        message={message ?? ''}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
