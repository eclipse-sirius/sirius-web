/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { useComponent } from '../extension/useComponent';
import { useData } from '../extension/useData';
import { ImpactAnalysisDialogContextProvider } from '../modals/impact-analysis/ImpactAnalysisDialogContext';
import { useRepresentationMetadata } from '../representationmetadata/useRepresentationMetadata';
import {
  GQLRepresentationMetadata,
  GQLRepresentationMetadataQueryData,
} from '../representationmetadata/useRepresentationMetadata.types';
import { useSelection } from '../selection/useSelection';
import { Panels } from './Panels';
import { RepresentationNavigation } from './RepresentationNavigation';
import { useEditingContextEventSubscription } from './useEditingContextEventSubscription';
import {
  GQLEditingContextEventPayload,
  GQLRepresentationRenamedEventPayload,
} from './useEditingContextEventSubscription.types';
import {
  RepresentationComponentProps,
  RepresentationMetadata,
  WorkbenchProps,
  WorkbenchState,
  WorkbenchViewContribution,
} from './Workbench.types';
import {
  representationFactoryExtensionPoint,
  workbenchMainAreaExtensionPoint,
  workbenchViewContributionExtensionPoint,
} from './WorkbenchExtensionPoints';

const useWorkbenchStyles = makeStyles()(() => ({
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

const isRepresentationRenamedEventPayload = (
  payload: GQLEditingContextEventPayload | null
): payload is GQLRepresentationRenamedEventPayload =>
  !!payload && payload.__typename === 'RepresentationRenamedEventPayload';

const getRepresentationMetadata = (data: GQLRepresentationMetadataQueryData | null): GQLRepresentationMetadata[] =>
  data?.viewer.editingContext.representations.edges.map((edge) => edge.node) ?? [];

export const Workbench = ({
  editingContextId,
  initialRepresentationSelected,
  onRepresentationSelected,
  readOnly,
}: WorkbenchProps) => {
  const { classes } = useWorkbenchStyles();
  const [state, setState] = useState<WorkbenchState>({
    id: crypto.randomUUID(),
    displayedRepresentationMetadata: initialRepresentationSelected,
    representationsMetadata: initialRepresentationSelected ? [initialRepresentationSelected] : [],
  });

  const { selection, setSelection } = useSelection();
  const { data: representationMetadataQueryData } = useRepresentationMetadata(editingContextId, selection);
  const { payload: editingContextEventPayload } = useEditingContextEventSubscription(editingContextId);

  const { data: representationFactories } = useData(representationFactoryExtensionPoint);
  useEffect(() => {
    if (isRepresentationRenamedEventPayload(editingContextEventPayload)) {
      const { representationId, newLabel } = editingContextEventPayload;
      const representationsMetadata = [...state.representationsMetadata];

      representationsMetadata.forEach((representationMetadata) => {
        if (representationMetadata.id === representationId) {
          representationMetadata.label = newLabel;
        }
      });

      setState((prevState) => ({
        ...prevState,
        representationsMetadata: representationsMetadata,
      }));
    }
  }, [editingContextEventPayload]);

  // When opening a representation
  useEffect(() => {
    const selectedElementRepresentationMetadata = getRepresentationMetadata(representationMetadataQueryData);
    if (
      selectedElementRepresentationMetadata.length > 0 &&
      selectedElementRepresentationMetadata[0]?.id !== state.displayedRepresentationMetadata?.id
    ) {
      const displayedRepresentationMetadata = selectedElementRepresentationMetadata[0];

      const representationsMetadata = [...state.representationsMetadata];
      const newRepresentationsMetadata = selectedElementRepresentationMetadata.filter(
        (selectedRepresentation) =>
          !representationsMetadata.find((representation) => selectedRepresentation.id === representation.id)
      );

      const newSelectedRepresentations = [...representationsMetadata, ...newRepresentationsMetadata];

      setState((prevState) => ({
        ...prevState,
        displayedRepresentationMetadata: displayedRepresentationMetadata ? displayedRepresentationMetadata : null,
        representationsMetadata: newSelectedRepresentations,
      }));
    }
  }, [representationMetadataQueryData, selection]);

  const onClose = (representationToHide: RepresentationMetadata) => {
    const previousIndex = state.representationsMetadata.findIndex(
      (representation) =>
        state.displayedRepresentationMetadata && representation.id === state.displayedRepresentationMetadata.id
    );
    const newRepresentationsMetadata = state.representationsMetadata.filter(
      (representation) => representation.id !== representationToHide.id
    );

    if (newRepresentationsMetadata.length === 0) {
      // There are no representations anymore
      setState((prevState) => ({
        ...prevState,
        displayedRepresentationMetadata: null,
        representationsMetadata: [],
      }));
    } else {
      const newIndex = newRepresentationsMetadata.findIndex(
        (representation) =>
          state.displayedRepresentationMetadata && representation.id === state.displayedRepresentationMetadata.id
      );

      if (newIndex !== -1) {
        // The previously displayed representation has not been closed
        setState((prevState) => ({
          ...prevState,
          representationsMetadata: newRepresentationsMetadata,
        }));
      } else if (newRepresentationsMetadata.length === previousIndex) {
        // The previous representation has been closed and it was the last one
        const displayedRepresentationMetadata = newRepresentationsMetadata[previousIndex - 1];
        setState((prevState) => ({
          ...prevState,
          displayedRepresentationMetadata: displayedRepresentationMetadata ? displayedRepresentationMetadata : null,
          representationsMetadata: newRepresentationsMetadata,
        }));
      } else {
        const displayedRepresentationMetadata = newRepresentationsMetadata[previousIndex];
        setState((prevState) => ({
          ...prevState,
          displayedRepresentationMetadata: displayedRepresentationMetadata ? displayedRepresentationMetadata : null,
          representationsMetadata: newRepresentationsMetadata,
        }));
      }
    }
  };

  useEffect(() => {
    if (
      state.displayedRepresentationMetadata &&
      state.displayedRepresentationMetadata.id !== initialRepresentationSelected?.id
    ) {
      onRepresentationSelected(state.displayedRepresentationMetadata);
    } else if (state.displayedRepresentationMetadata === null && initialRepresentationSelected) {
      onRepresentationSelected(null);
    }
  }, [onRepresentationSelected, initialRepresentationSelected, state.displayedRepresentationMetadata]);

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

  const onRepresentationClick = (representation: RepresentationMetadata) => {
    setSelection({ entries: [{ id: representation.id }] });
  };

  if (state.displayedRepresentationMetadata) {
    const displayedRepresentationMetadata = state.displayedRepresentationMetadata;
    const RepresentationComponent = representationFactories
      .map((representationFactory) => representationFactory(displayedRepresentationMetadata))
      .find((component) => component != null);
    const props: RepresentationComponentProps = {
      editingContextId,
      readOnly,
      representationId: displayedRepresentationMetadata.id,
    };
    if (RepresentationComponent) {
      main = (
        <div className={classes.representationArea} data-testid="representation-area">
          <RepresentationNavigation
            representations={state.representationsMetadata}
            displayedRepresentation={displayedRepresentationMetadata}
            onRepresentationClick={onRepresentationClick}
            onClose={onClose}
          />
          <RepresentationComponent key={`${editingContextId}#${displayedRepresentationMetadata.id}`} {...props} />
        </div>
      );
    }
  }

  return (
    <ImpactAnalysisDialogContextProvider>
      <Panels
        editingContextId={editingContextId}
        readOnly={readOnly}
        leftContributions={workbenchViewLeftSideContributions}
        leftPanelInitialSize={25}
        rightContributions={workbenchViewRightSideContributions}
        rightPanelInitialSize={25}
        mainArea={main}
      />
    </ImpactAnalysisDialogContextProvider>
  );
};
