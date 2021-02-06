/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { HORIZONTAL, Panels, SECOND_PANEL } from 'core/panels/Panels';
import { ExplorerWebSocketContainer } from 'explorer/ExplorerWebSocketContainer';
import { PropertiesWebSocketContainer } from 'properties/PropertiesWebSocketContainer';
import React, { useEffect } from 'react';
import { OnboardArea } from 'workbench/OnboardArea';
import { RepresentationArea } from 'workbench/RepresentationArea';
import { Representation, Selection, WorkbenchProps } from 'workbench/Workbench.types';
import {
  HideRepresentationEvent,
  ShowRepresentationEvent,
  UpdateSelectionEvent,
  WorkbenchContext,
  WorkbenchEvent,
  workbenchMachine,
} from 'workbench/WorkbenchMachine';

const useWorkbenchStyles = makeStyles((theme) => ({
  main: {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
  },
}));

export const Workbench = ({
  editingContextId,
  initialRepresentationSelected,
  onRepresentationSelected,
  readOnly,
}: WorkbenchProps) => {
  const classes = useWorkbenchStyles();
  const [{ context }, dispatch] = useMachine<WorkbenchContext, WorkbenchEvent>(workbenchMachine, {
    context: {
      displayedRepresentation: initialRepresentationSelected,
      representations: initialRepresentationSelected ? [initialRepresentationSelected] : [],
    },
  });
  const { selection, representations, displayedRepresentation } = context;

  const setSelection = (selection: Selection) => {
    const updateSelectionEvent: UpdateSelectionEvent = { type: 'UPDATE_SELECTION', selection };
    dispatch(updateSelectionEvent);
  };

  const onRepresentationClick = (representation: Representation) => {
    const showRepresentationEvent: ShowRepresentationEvent = { type: 'SHOW_REPRESENTATION', representation };
    dispatch(showRepresentationEvent);
  };

  const onClose = (representation: Representation) => {
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

  const explorer = (
    <ExplorerWebSocketContainer editingContextId={editingContextId} selection={selection} setSelection={setSelection} />
  );

  const properties = <PropertiesWebSocketContainer projectId={editingContextId} selection={selection} />;
  let main = <OnboardArea projectId={editingContextId} selection={selection} setSelection={setSelection} />;
  if (displayedRepresentation) {
    main = (
      <RepresentationArea
        projectId={editingContextId}
        representations={representations}
        displayedRepresentation={displayedRepresentation}
        selection={selection}
        setSelection={setSelection}
        onRepresentationClick={onRepresentationClick}
        onClose={onClose}
        readOnly={readOnly}
      />
    );
  }

  return (
    <Panels
      orientation={HORIZONTAL}
      firstPanel={explorer}
      secondPanel={
        <div className={classes.main} data-testid="representationAndProperties">
          <Panels
            orientation={HORIZONTAL}
            resizablePanel={SECOND_PANEL}
            firstPanel={main}
            secondPanel={properties}
            initialResizablePanelSize={300}
          />
        </div>
      }
      initialResizablePanelSize={300}
    />
  );
};
