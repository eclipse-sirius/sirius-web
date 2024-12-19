/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo and others.
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
import { useSelection, WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import {
  FormBasedView,
  FormContext,
  GQLForm,
  GQLList,
  GQLRepresentationsEventPayload,
  GQLTree,
  GQLWidget,
  ListPropertySection,
  TreePropertySection,
} from '@eclipse-sirius/sirius-components-forms';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { RepresentationsViewState } from './RepresentationsView.types';
import { useRepresentationsViewSubscription } from './useRepresentationsViewSubscription';
import { GQLFormRefreshedEventPayload } from './useRepresentationsViewSubscription.types';

const useRepresentationsViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  content: {
    padding: theme.spacing(1),
  },
}));

const isList = (widget: GQLWidget | undefined): widget is GQLList => widget && widget.__typename === 'List';
const isTree = (widget: GQLWidget | undefined): widget is GQLTree => widget && widget.__typename === 'TreeWidget';
const isFormRefreshedEventPayload = (
  payload: GQLRepresentationsEventPayload
): payload is GQLFormRefreshedEventPayload => payload && payload.__typename === 'FormRefreshedEventPayload';

export const RepresentationsView = ({ editingContextId, readOnly }: WorkbenchViewComponentProps) => {
  const [state, setState] = useState<RepresentationsViewState>({
    currentSelection: { entries: [] },
    form: null,
  });

  const { selection } = useSelection();

  /**
   * Displays another form if the selection indicates that we should display another properties view.
   */
  const currentSelectionKey: string = state.currentSelection.entries
    .map((entry) => entry.id)
    .sort()
    .join(':');
  const newSelectionKey: string = selection.entries
    .map((entry) => entry.id)
    .sort()
    .join(':');
  useEffect(() => {
    if (selection.entries.length > 0 && currentSelectionKey !== newSelectionKey) {
      setState((prevState) => ({ ...prevState, currentSelection: selection }));
    } else if (selection.entries.length === 0) {
      setState((prevState) => ({ ...prevState, currentSelection: { entries: [] } }));
    }
  }, [currentSelectionKey, newSelectionKey]);

  const objectIds: string[] = state.currentSelection.entries.map((entry) => entry.id);
  const skip = objectIds.length === 0;

  const { payload, complete } = useRepresentationsViewSubscription(editingContextId, objectIds, skip);
  useEffect(() => {
    if (isFormRefreshedEventPayload(payload)) {
      setState((prevState) => ({ ...prevState, form: payload.form }));
    }
  }, [payload]);

  const { classes } = useRepresentationsViewStyles();

  const extractPlainList = (props: WorkbenchViewComponentProps, form: GQLForm): JSX.Element => {
    const widget: GQLWidget | undefined = form.pages[0]?.groups[0]?.widgets[0];
    if (isList(widget)) {
      return (
        <div className={classes.content}>
          <ListPropertySection
            editingContextId={props.editingContextId}
            formId={form.id}
            readOnly={props.readOnly}
            widget={widget}
          />
        </div>
      );
    } else if (isTree(widget)) {
      return (
        <div className={classes.content}>
          <TreePropertySection
            editingContextId={props.editingContextId}
            formId={form.id}
            readOnly={props.readOnly}
            widget={widget}
          />
        </div>
      );
    } else {
      return <div className={classes.content} />;
    }
  };

  if (!state.form || complete) {
    return (
      <div className={classes.idle}>
        <Typography variant="subtitle2">No object selected</Typography>
      </div>
    );
  }
  return (
    <div data-representation-kind="form-representation-list">
      <FormContext.Provider
        value={{
          payload: payload,
        }}>
        <FormBasedView
          editingContextId={editingContextId}
          form={state.form}
          readOnly={readOnly}
          postProcessor={extractPlainList}
        />
      </FormContext.Provider>
    </div>
  );
};
