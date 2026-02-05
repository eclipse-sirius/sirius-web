/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import {
  FormBasedView,
  FormContext,
  GQLForm,
  GQLFormRefreshedEventPayload,
  Group,
} from '@eclipse-sirius/sirius-components-forms';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DiagramFilterFormProps, DiagramFilterViewState } from './DiagramFilterForm.types';
import { useDiagramFilterSubscription } from './useDiagramFilterSubscription';
import {
  GQLDiagramFilterEventPayload,
  GQLFormCapabilitiesRefreshedEventPayload,
} from './useDiagramFilterSubscription.types';

const useDiagramFilterViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  content: {
    padding: theme.spacing(1),
  },
}));

const isFormRefreshedEventPayload = (payload: GQLDiagramFilterEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload?.__typename === 'FormRefreshedEventPayload';

const isFormCapabilitiesRefreshedEventPayload = (
  payload: GQLDiagramFilterEventPayload
): payload is GQLFormCapabilitiesRefreshedEventPayload =>
  payload.__typename === 'FormCapabilitiesRefreshedEventPayload';

export const DiagramFilterForm = ({ editingContextId, diagramId }: DiagramFilterFormProps) => {
  const [state, setState] = useState<DiagramFilterViewState>({
    form: null,
    canEdit: false,
  });

  const { payload, complete } = useDiagramFilterSubscription(editingContextId, [diagramId]);
  useEffect(() => {
    if (payload && isFormRefreshedEventPayload(payload)) {
      setState((prevState) => ({ ...prevState, form: payload.form }));
    } else if (payload && isFormCapabilitiesRefreshedEventPayload(payload)) {
      setState((prevState) => ({ ...prevState, canEdit: payload.capabilities.canEdit }));
    }
  }, [payload]);

  const { classes } = useDiagramFilterViewStyles();

  const extractFirstGroup = (editingContextId: string, form: GQLForm, readOnly: boolean): JSX.Element => {
    const group = form.pages[0]?.groups[0];
    if (group) {
      return (
        <div className={classes.content}>
          <Group editingContextId={editingContextId} formId={form.id} readOnly={readOnly} group={group} />
        </div>
      );
    } else {
      return <div className={classes.content} />;
    }
  };

  if (!state.form || complete) {
    return null;
  }
  return (
    <div data-representation-kind="form-diagram-filter">
      <FormContext.Provider
        value={{
          payload: payload,
        }}>
        <FormBasedView
          editingContextId={editingContextId}
          form={state.form}
          initialSelectedPageLabel={null}
          readOnly={!state.canEdit}
          postProcessor={extractFirstGroup}
        />
      </FormContext.Provider>
    </div>
  );
};
